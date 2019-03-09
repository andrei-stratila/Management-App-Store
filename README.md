# ManagementAppStore

A desktop java application that simulates a shop from the perspective of an administrator or a customer. The administrator can manage effectively the products in the store, having the option to view them by different criteria, adding new products or editing those that already exists. As far as customer is concerned, he has a ShoppingCart and a WishList to organize the products that he bought or want to buy.

## Getting Started

Download the ZIP archive or clone this repository on your device.
```
git clone https://github.com/andrei-stratila/management-app-store
```
## NetBeans Project

Open the project folder as a NetBeans project (NetBeans was the IDE used during development). After succesfully opening the project, run it and the application should start.

## Description

The program takes as input two files named: store.txt, which contains information about the shop products and customers.txt, which contains a list of customers name. Those information are used to simulate the shop.

When the application starts, the user has to select those two files from computer. For the Administration tab, you select Admin from the top bar and there you have options to manage the products. For the customer tab, you will need to select your name from the Customer list from the top bar, and after you can check your shopping list using the ShoppingCart and WishList from the sidepanel.

## Implementation Notes

The application was written in Java with strong regard to OOP principles. There are various design patterns implemented to assure the quality of the app, some of them being: Singleton, Visitor, Observer, Strategy.
