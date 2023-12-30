package com.btl_tkxdpm.export;
import com.btl_tkxdpm.AttendanceDB.IAttendanceDB;
import com.btl_tkxdpm.AttendanceDB.OnSiteAttendanceDB;
import com.btl_tkxdpm.SwitchScreener;
import com.btl_tkxdpm.entity.NhanVienAttendance;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ExportController implements Initializable {
    private IAttendanceDB attendanceDB;
    @FXML
    private ChoiceBox<?> donViSearch;
    @FXML
    private ChoiceBox namSearch;
    @FXML
    private ChoiceBox loaiNhanSu;
    @FXML
    private TableColumn<NhanVienAttendance, String> tableChucDanh;

    @FXML
    private TableColumn<NhanVienAttendance, String> tableDiMuonVeSom;

    @FXML
    private TableColumn<NhanVienAttendance, String> tableTongSoBuoiLam;

    @FXML
    private TableColumn<NhanVienAttendance, String> tableMaNV;

    @FXML
    private TableColumn<NhanVienAttendance, String> tableTen;
    @FXML
    private TableColumn<NhanVienAttendance, String> tableNgay;

    @FXML
    private TableView tableNhanVien;
    @FXML
    private TableView tableCongNhan;

    @FXML
    private ChoiceBox<String> thangSearch;

    @FXML
    void clickQuayLai(MouseEvent event) {
        SwitchScreener.switchScreen("/com/btl_tkxdpm/manHinhChinh.fxml");
    }

    @FXML
    void clickXacNhan(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Định dạng báo cáo");
        alert.setHeaderText("Bạn mốn xuất báo cáo dưới định dạng nào:");

        // Create two buttons
        ButtonType buttonTypeExcel=new ButtonType("Excel");
        ButtonType buttonTypeCSV = new ButtonType("CSV");

        // Set buttons in the alert
        alert.getButtonTypes().setAll(buttonTypeExcel, buttonTypeCSV);

        // Show the alert and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeCSV) {
                System.out.println("User clicked CSV");
                //String donVi = donViSearch.getValue().toString();
                String thang = thangSearch.getValue().toString();
                String nam = namSearch.getValue().toString();
                CsvExporter.exportToCsv(attendanceDB.getListAttendance(), "Báo cáo "+thang+"_"+nam+".csv" );
                // Add your code for handling "Yes" option
            } else if (response == buttonTypeExcel) {
                System.out.println("User clicked No");
                // Add your code for handling "No" option
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attendanceDB = new OnSiteAttendanceDB();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        tableCongNhan.setItems(attendanceDB.getListCongNhanAttendance());
        tableNhanVien.setItems(attendanceDB.getListNhanVienAttendace());
        tableTen.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNhanVien().getHoTen()));
        //tableChucDanh.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNhanVien().getChucDanh()));
        tableMaNV.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNhanVien().getMaNhanVien()));
        //tableNgay.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDay().format(dateFormatter)));
        thangSearch.setItems(FXCollections.observableArrayList(
                "Tất cả", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        ));
        namSearch.setItems(FXCollections.observableArrayList(
                "Tất cả","2015","2016","2017","2018","2019","2020","2021","2022","2023"
        ));
        thangSearch.setValue("Tất cả");
        namSearch.setValue("Tất cả");
        loaiNhanSu.setItems(FXCollections.observableArrayList(
                "Nhân viên văn phòng", "Công nhân"
        ));
        loaiNhanSu.setValue("Công nhân");
    }
}
