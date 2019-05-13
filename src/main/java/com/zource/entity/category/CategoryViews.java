/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.entity.category;


//JSON Views
public class CategoryViews {

    public static class ShortView {
    } // id + name

    public static class ChildrenView extends ShortView {
    } // + children categories

    public static class ParentsView extends ShortView {
    }


}
