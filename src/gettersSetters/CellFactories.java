/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gettersSetters;

import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author Admin
 */
public class CellFactories {
    public  Callback<TableColumn, TableCell> cellFactoryCheckBox = new Callback<TableColumn, TableCell>() {

        @Override
        public TableCell call(final TableColumn param) {
            final CheckBox checkBox = new CheckBox();
            TableView tableView = new TableView();
            final TableCell cell = new TableCell() {

                public void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item == null){
                        checkBox.setVisible(false);

                    }else {
                        checkBox.setVisible(true);
                        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                  

                            }
                        });


                    }
                }
            };
            cell.setGraphic(checkBox);
            return cell;
        }
    };
}
