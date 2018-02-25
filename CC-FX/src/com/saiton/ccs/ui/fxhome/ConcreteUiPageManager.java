package com.saiton.ccs.ui.fxhome;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Page Manager
 * @author Saiton
 */
public class ConcreteUiPageManager implements UiPageManager {

    private AnchorPane parent;
    private boolean initSuccess = false;
    private HashMap<String, Node> pageMap;
    private String startPage = "";
    private boolean isStartPageSet = false;

    @Override
    public boolean initPageManager(AnchorPane parentNode) {

        if (parentNode == null) {
            return false;
        }

        parent = parentNode;
        pageMap = new HashMap<>();
        initSuccess = true;

        return true;
    }

    @Override
    public boolean registerStartupUiPage(UiPage page) {
        boolean ret = registerUiPage(page);
        if (ret) {
            startPage = page.getUiName();
            isStartPageSet = true;
        }
        return false;
    }

    @Override
    public boolean registerUiPage(UiPage page) {
        if (!initSuccess || page == null) {
            return false;
        }
        try {
            Node node = page.getUi();
            
            pageMap.put(page.getUiName(), node);
            
            //add to parent Anchorpane
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            parent.getChildren().add(node);
            
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean hideAllUi() {
        try {
            pageMap.values().stream().forEach((node) -> {
                node.setVisible(false);
            });
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    @Override
    public boolean navigateTo(String uiName) {
       try {
           hideAllUi();
           pageMap.get(uiName).setVisible(true);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean start() {
        if (isStartPageSet) {
            return navigateTo(startPage);
        } else {
            return false;
        }
    }

    public static UiPage buildUiPage(Node node, String name) {
        return new UiPageImpl(node, name);
    }

    private static class UiPageImpl implements UiPage {

        private final Node node;
        private final String name;

        public UiPageImpl(Node node, String name) {
            this.node = node;
            this.name = name;
        }

        @Override
        public Node getUi() {
            return node;
        }

        @Override
        public String getUiName() {
            return name;
        }
    }
}
