package com.ixbob.thepit.gui;

public interface OpenableGUI {
    void display(String title); //调用下面三个

    void initFrame(String title);

    void open();

    void initContent();
}
