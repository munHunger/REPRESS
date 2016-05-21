/**
 * 
 */
package se.munhunger.repress.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Predicate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import se.munhunger.repress.project.Project;
import se.munhunger.repress.project.Requisite;

/**
 * @author munhunger
 * 		
 */
public class RequisiteTable extends TableView<Requisite>
{
	private ObservableList<Requisite> masterData;
	private ArrayList<Predicate<Requisite>> predicates = new ArrayList<>();
	private Predicate<Requisite> masterPredicate;
	
	private FilteredList<Requisite> filteredData;
	
	public RequisiteTable(Project parent)
	{
		super.getColumns().add(buildColumn("Name", param ->
		{
			return new SimpleStringProperty(param.getValue().getTitle());
		} , req ->
		{
			return req.getTitle();
		}));
		super.getColumns().add(buildColumn("Description", param ->
		{
			return new SimpleStringProperty(param.getValue().getDescription().length() < 15
					? param.getValue().getDescription() : param.getValue().getDescription().substring(0, 15) + "...");
		} , req ->
		{
			return req.getDescription();
		}));
		super.getColumns().add(buildColumn("Creator", param ->
		{
			return new SimpleStringProperty(param.getValue().getCreator().getName());
		} , req ->
		{
			return req.getCreator().getName();
		}));
		super.getColumns().add(buildColumn("Responsible user", param ->
		{
			return new SimpleStringProperty(param.getValue().getResponisbleUser().getName());
		} , req ->
		{
			return req.getResponisbleUser().getName();
		}));
		super.getColumns().add(buildColumn("Creation date", param ->
		{
			Date d = new Date(param.getValue().getCreationDate());
			return new SimpleStringProperty(d.toString());
		} , req ->
		{
			return new Date(req.getCreationDate()).toString();
		}));
		
		masterData = FXCollections.observableArrayList(parent.getRequisites());
		masterPredicate = req ->
		{
			for (Predicate<Requisite> pred : predicates)
				if (!pred.test(req))
					return false;
			return true;
		};
		filteredData = new FilteredList<>(masterData, masterPredicate);
		SortedList<Requisite> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(super.comparatorProperty());
		super.setItems(sortedData);
		super.setRowFactory(tableView ->
		{
			TableRow<Requisite> row = new TableRow<>();
			row.setOnMouseClicked(event ->
			{
				if (event.getClickCount() > 1 && event.getButton() == MouseButton.PRIMARY && !row.isEmpty())
				{
					Requisite clickedReq = row.getItem();
					System.out.println("Open: " + clickedReq.getTitle());
				}
			});
			return row;
		});
	}
	
	private TableColumn<Requisite, String> buildColumn(String title,
			Callback<CellDataFeatures<Requisite, String>, ObservableValue<String>> mapper,
			Function<Requisite, String> valueMap)
	{
		TableColumn<Requisite, String> result = new TableColumn<>();
		result.setCellValueFactory(mapper);
		TextField filterField = new TextField();
		filterField.textProperty().addListener((observable, oldValue, newValue) ->
		{
			masterPredicate = req ->
			{
				for (Predicate<Requisite> pred : predicates)
					if (!pred.test(req))
						return false;
				return true;
			};
			filteredData.setPredicate(masterPredicate);
		});
		predicates.add(req ->
		{
			return valueMap.apply(req).toUpperCase().contains(filterField.getText().toUpperCase());
		});
		Label label = new Label(title);
		VBox graphic = new VBox();
		graphic.setAlignment(Pos.CENTER);
		graphic.getChildren().addAll(label, filterField);
		result.setGraphic(graphic);
		return result;
	}
}
