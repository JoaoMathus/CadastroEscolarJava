module br.com.escola.cadastro.cadastroescolarjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens br.com.escola.cadastro.cadastroescolarjava.entidades to javafx.base;

    exports br.com.escola.cadastro.cadastroescolarjava;
    opens br.com.escola.cadastro.cadastroescolarjava to javafx.base, javafx.fxml;
}