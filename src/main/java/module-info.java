module br.com.escola.cadastro.cadastroescolarjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens br.com.escola.cadastro.cadastroescolarjava to javafx.fxml;
    exports br.com.escola.cadastro.cadastroescolarjava;
}