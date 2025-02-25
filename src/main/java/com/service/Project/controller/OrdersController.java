package com.service.Project.controller;


import com.service.Project.bo.BOFactory;
import com.service.Project.bo.custom.CustomerBo;
import com.service.Project.bo.custom.ItemBo;
import com.service.Project.bo.custom.OrdersBo;

import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.service.Project.model.CustomerDTO;
import com.service.Project.model.ItemDTO;
import com.service.Project.model.OrderDTO;
import com.service.Project.model.OrderDetailsDTO;
import com.service.Project.model.tm.CartTM;


import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    // FXML controls to interact with the GUI elements
    @FXML
    private ComboBox<String> cmbCustomerId;
    @FXML
    private ComboBox<String> cmbItemId;
    @FXML
    private TableView<CartTM> tblCart;
    @FXML
    private TableColumn<CartTM, String> colItemId;
    @FXML
    private TableColumn<CartTM, String> colName;
    @FXML
    private TableColumn<CartTM, Integer> colQuantity;
    @FXML
    private TableColumn<CartTM, Double> colPrice;
    @FXML
    private TableColumn<CartTM, Double> colTotal;
    @FXML
    private TableColumn<CartTM,Void> colAction;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblItemName;
    @FXML
    private Label lblItemPrice;
    @FXML
    private Label lblItemQty;
    @FXML
    private Label lblOrderId;
    @FXML
    private Label orderDate;
    @FXML
    private TextField txtAddToCartQty;

    OrdersBo ordersBo = (OrdersBo) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);
    CustomerBo customerBo = (CustomerBo) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    ItemBo itemBo = (ItemBo) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void setCellValues() {

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblCart.setItems(cartTMS);
    }


    private void refreshPage() throws SQLException {

        lblOrderId.setText(ordersBo.getNextId());


        orderDate.setText(LocalDate.now().toString());

        loadCustomerIds(); //
        loadItemId(); //

        cmbCustomerId.getSelectionModel().clearSelection(); //
        cmbItemId.getSelectionModel().clearSelection(); //
        lblItemName.setText("");
        lblItemQty.setText("");
        lblItemPrice.setText("");
        txtAddToCartQty.setText("");
        lblCustomerName.setText("");

        cartTMS.clear();


        tblCart.refresh();
    }


    private void loadItemId() throws SQLException {
        ArrayList<String> itemIds = itemBo.getAllItemIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        cmbItemId.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerBo.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustomerId.setItems(observableList);
    }


    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = cmbItemId.getValue();

        // If no item is selected, show an error alert and return
        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select item..!").show();
            return; // Exit the method if no item is selected.
        }

        String quantityPattern = "^[0-9]+$";
//        String pricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
//        right :- 500.00. 500.65, 500,
//        wrong :- 787.8, 6777.9999

        boolean isValidQty = txtAddToCartQty.getText().matches(quantityPattern);

        if (!isValidQty){
            new Alert(Alert.AlertType.ERROR,"Invalid qty").show();
            return;
        }

        String itemName = lblItemName.getText();
        int cartQty = Integer.parseInt(txtAddToCartQty.getText());
        int qtyOnHand = Integer.parseInt(lblItemQty.getText());

        // Check if there are enough items in stock; if not, show an error alert and return
        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough items..!").show();
            return; // Exit the method if there's insufficient stock.
        }

        // Clear the text field for adding quantity after retrieving the input value.
        txtAddToCartQty.setText("");

        double unitPrice = Double.parseDouble(lblItemPrice.getText());
        double total = unitPrice * cartQty;

        // Loop through each item in cart's observable list.
        for (CartTM cartTM : cartTMS) {

            // Check if the item is already in the cart
            if (cartTM.getItemId().equals(selectedItemId)) {
                // Update the existing CartTM object in the cart's observable list with the new quantity and total.
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty); // Add the new quantity to the existing quantity in the cart.
                cartTM.setTotal(unitPrice * newQty); // Recalculate the total price based on the updated quantity

                // Refresh the table to display the updated information.
                tblCart.refresh();
                return; // Exit the method as the cart item has been updated.
            }
        }


        // Create a "Remove" button for the item to allow it to be removed from the cart later.
        Button btn = new Button("Remove");

        // If the item does not already exist in the cart, create a new CartTM object to represent it.
        CartTM newCartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                unitPrice,
                total,
                btn
        );
        System.out.println(newCartTM);

        // Set an action for the "Remove" button, which removes the item from the cart when clicked.
        btn.setOnAction(actionEvent -> {

            // Remove the item from the cart's observable list (cartTMS).
            cartTMS.remove(newCartTM);

            // Refresh the table to reflect the removal of the item.
            tblCart.refresh();
        });

        // Add the newly created CartTM object to the cart's observable list.
        cartTMS.add(newCartTM);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to cart..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());
        String customerId = cmbCustomerId.getValue();


        ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();


        for (CartTM cartTM : cartTMS) {


            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            );

            orderDetailsDTOS.add(orderDetailsDTO);
        }

        OrderDTO orderDTO = new OrderDTO(
                orderId,
                customerId,
                dateOfOrder,
                orderDetailsDTOS
        );

        boolean isSaved = ordersBo.save(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();

            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();

        if (selectedCustomerId == null) {
            return;
        }

        CustomerDTO customerDTO = customerBo.findById(selectedCustomerId);

        if (customerDTO != null) {

            lblCustomerName.setText(customerDTO.getName());
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();

        if (selectedItemId == null) {
            return;
        }

        ItemDTO itemDTO = itemBo.findById(selectedItemId);

        if (itemDTO != null) {

            lblItemName.setText(itemDTO.getItemName());
            lblItemQty.setText(String.valueOf(itemDTO.getQuantity()));
            lblItemPrice.setText(String.valueOf(itemDTO.getPrice()));
        }
    }

}

