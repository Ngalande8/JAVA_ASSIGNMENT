package com.example.application.views.list;

import com.example.application.data.entity.Equipment;
import com.example.application.data.service.EquipmentService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PageTitle("Equipment |  ZescoIssueTracker")
@Route(value = "equipment", layout = MainLayout.class)
@PermitAll

public class ListView extends VerticalLayout {
    Grid<Equipment> grid = new Grid<>(Equipment.class);
    TextField filterText = new TextField();
    EquipmentForm form;
    private EquipmentService service;

    public ListView(EquipmentService service){
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
          getToolbar(),
          getContent()
        );
        
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setEquipment(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllEquipment(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2,grid);
        content.setFlexGrow(1,form);
        content.addClassName("content");
        content.setSizeFull();
        
        return content;
    }

    private void configureForm() {
        form = new EquipmentForm(service.findAllEquipment(null));
        form.setWidth("25em");

        form.addListener(EquipmentForm.SaveEvent.class, this::saveEquipment);
        form.addListener(EquipmentForm.DeleteEvent.class, this::deleteEquipment);
        form.addListener(EquipmentForm.DeleteEvent.class, e -> closeEditor());

    }

    private void deleteEquipment(EquipmentForm.DeleteEvent event) {
        service.deleteEquipment(event.getEquipment());
        updateList();
        closeEditor();
    }

    private void saveEquipment(EquipmentForm.SaveEvent event) {
        service.saveEquipment(event.getEquipment());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEquipmentButton = new Button("Add Equipment");
        addEquipmentButton.addClickListener(e -> addEquipment());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEquipmentButton);
        toolbar.addClassName("toolbar");
        return null;
    }

    private void addEquipment() {
        grid.asSingleSelect().clear();
        editEquipment(new Equipment());
    }

    private void configureGrid(){
        grid.addClassName("grid");
        grid.setSizeFull();
        grid.setColumns("equipName","description", "location","inspectionDate");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e-> editEquipment(e.getValue()));

    }

    private void editEquipment(Equipment equipment) {
        if(equipment == null){
            closeEditor();
        } else{
            form.setEquipment(equipment);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}