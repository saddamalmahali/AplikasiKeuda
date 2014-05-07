/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuda.view;

import com.keuda.model.ItemMaster;
import com.keuda.model.ItemMasterStructure;

/**
 *
 * @author user
 */
public class ItemMasterStructureForTree {
    ItemMasterStructure[] sr;
    java.util.Vector vector;
    ItemMaster[] ims;

    //  update 08/01/2014
    ItemMaster[] itemSuper;
    ItemMaster[] itemSub;

    //  constructor update 08/01/2014
        public ItemMasterStructureForTree() {
        }

    public ItemMasterStructureForTree(ItemMasterStructure[] sr, java.util.Vector v) {
        this.sr = sr;
        this.vector = v;
    }

    public void setSuper(ItemMaster[] ims){
        this.ims = ims;
    }
    public ItemMaster[] getSuper(){
        return ims;
    }
    public ItemMasterStructure[] getStructure(){
        return sr;
    }
    public java.util.Vector getItemMaster(){
        return vector;
    }
    public java.util.Vector getItemMasterID(){
        return vector;
    }

        public ItemMaster[] getItemSuper() {
            return itemSuper;
        }

        public void setItemSuper(ItemMaster[] itemSuper) {
            this.itemSuper = itemSuper;
        }

        public ItemMaster[] getItemSub() {
            return itemSub;
        }

        public void setItemSub(ItemMaster[] itemSub) {
            this.itemSub = itemSub;
        }
}
