package com.vector.myokhttputils.typetest;

/**
 * Created by Vector
 * on 2017/9/25 0025.
 */

public abstract class TypeImpl<T> implements IType<T> {
    public void paser() {
        System.out.println(Utils.find(getClass()).toString());
    }

}
