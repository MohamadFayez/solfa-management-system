package com.truemega.solfa.view.utils;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;


import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

public class ViewUtils {
    /**Method to get Iterator*/
    public static RowIterator getSelectedNodeIterator(RichTreeTable treeTab) {
        if (treeTab != null &&
            treeTab.getSelectedRowKeys() != null) {
            for (Object rKey : treeTab.getSelectedRowKeys()) {
                treeTab.setRowKey(rKey);
                return ((JUCtrlHierNodeBinding)treeTab.getRowData()).getRowIterator();
            }
        }
        return null;
    }

    /**Method to get Node Key*/
    public static Key getSelectedNodeKey(RichTreeTable treeTab) {
        if (treeTab != null &&
            treeTab.getSelectedRowKeys() != null) {
            for (Object rKey : treeTab.getSelectedRowKeys()) {
                treeTab.setRowKey(rKey);
                return ((JUCtrlHierNodeBinding)treeTab.getRowData()).getRowKey();
            }
        }
        return null;
    }
}
