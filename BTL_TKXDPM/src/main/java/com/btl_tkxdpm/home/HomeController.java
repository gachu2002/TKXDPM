package com.btl_tkxdpm.home;

import com.btl_tkxdpm.AttendanceDB.IAttendanceDB;
import com.btl_tkxdpm.AttendanceDB.OnSiteAttendanceDB;
import com.btl_tkxdpm.SwitchScreener;
import com.btl_tkxdpm.add.ImportExcel;
import com.btl_tkxdpm.entity.NhanVienAttendance;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private IAttendanceDB attendanceDB;
    @FXML
    private TextField chucDanhAccount;

    @FXML
    private ChoiceBox<String> donViSearch;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField maNVAccount;
    @FXML
    private ChoiceBox<String> namSearch;
    @FXML
    private TextField search;

    @FXML
    private TableColumn<NhanVienAttendance, String> tableChucDanh;

    @FXML
    private TableColumn<NhanVienAttendance, String> tableGioRa;

    @FXML
    private TableColumn<NhanVienAttendance, String> tableGioVao;

    @FXML
    private TableColumn<NhanVienAttendance, String> tableMaNV;

    @FXML
    private TableColumn<NhanVienAttendance, String> tableTen;
    @FXML
    private TableColumn<NhanVienAttendance, String> tableNgay;
    @FXML
    private TextField tenAccount;

    @FXML
    private ChoiceBox<String> thangSearch;

    @FXML
    private Button themNhanVienButton;

    @FXML
    private Button thongKeButton;

    @FXML
    private Button xuatBaoCaoButton;

    @FXML
    private TableView<NhanVienAttendance> tableView;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        attendanceDB = new OnSiteAttendanceDB();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        tableView.setItems(attendanceDB.getListAttendance());
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                NhanVienAttendance selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    System.out.println("Double-clicked on: " + selected.getNhanVien().getMaNhanVien() );

                }
            }
        });
        tableTen.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNhanVien().getHoTen()));
        tableChucDanh.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNhanVien().getChucDanh()));
        tableMaNV.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNhanVien().getMaNhanVien()));
        tableGioRa.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLoaiChamCong()));
        tableGioVao.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGioVao().format(timeFormatter)));
        tableNgay.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDay().format(dateFormatter)));
        thangSearch.setItems(FXCollections.observableArrayList(
                "Tất cả", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        ));
        namSearch.setItems(FXCollections.observableArrayList(
                "Tất cả","2015","2016","2017","2018","2019","2020","2021","2022","2023"
        ));
        thangSearch.setValue("Tất cả");
        namSearch.setValue("Tất cả");
    }
    @FXML
    void clickXuatBaoCao(MouseEvent event) {
        SwitchScreener.switchScreen("/com/btl_tkxdpm/xuatBaoCao.fxml");
        }
    @FXML
    void clickThemChamCong(MouseEvent event){SwitchScreener.switchScreen("/com/btl_tkxdpm/danhSachNhanVien.fxml");}
    @FXML
    void clickImportExcel(MouseEvent event){
        FileChooser fileChooser = new FileChooser();

        // Set initial directory (optional)
        File initialDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(initialDirectory);

        // Show open dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Handle the selected file
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            ImportExcel.importExcel(selectedFile.getAbsolutePath());
        } else {
            System.out.println("File selection canceled.");
        }
    }
}
