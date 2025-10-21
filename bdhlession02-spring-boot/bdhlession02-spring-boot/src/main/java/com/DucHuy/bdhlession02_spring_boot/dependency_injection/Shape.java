package com.DucHuy.bdhlession02_spring_boot.dependency_injection;

interface Shape {
    void draw();
}
class CircleShape implements Shape{
    @Override
    public void draw() {
        System.out.println("CircleShape draw");
    }
}
class RectangleShape implements Shape{
    @Override
    public void draw() {
        System.out.println("RectangleShape draw");
    }
}

