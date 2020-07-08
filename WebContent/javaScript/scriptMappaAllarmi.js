
function initialize()
{

	var objectVoloMap = document.getElementById('formBody:infoControllerToJs').value;
	objectVoloJsonMap = JSON.parse(objectVoloMap);
	
	singleElementoSelect = document.getElementById('formBody:infoControllerToJsSingleElemento').value;
	
	var objectVoloInfo = document.getElementById('formBody:infoJsToController').value;
	objectVoloJsonInfo = JSON.parse(objectVoloInfo);
	
	bounds = new google.maps.LatLngBounds(new google.maps.LatLng(objectVoloJsonMap.minLat, objectVoloJsonMap.minLon), new google.maps.LatLng(objectVoloJsonMap.maxLat, objectVoloJsonMap.maxLon));//serve per ingandire in funzione delle coordinate massime
	map=new google.maps.Map(document.getElementById("googleMapAllarmi"),mapProp);
	map.setCenter(bounds.getCenter());
	map.fitBounds(bounds);

//	//define trip
	getTracciatoVolo(objectVoloJsonMap.tracciatoVolo);
	
	//set MARKER
//	setMarkerPercorso(objectVoloJsonMap.tracciatoVolo);
	
//	//define grid
//	getGrigliaVolo(objectVoloJsonMap.grigliaMappa);
	
	//define circle
	getCircleCoordinata(objectVoloJsonMap.tracciatoVolo, singleElementoSelect);
}

function ShowImage()
{
    var div = document.getElementById('popupRangeAllarmi');
    div.style.display = "block";
}

function HideImage()
{
    document.getElementById('popupRangeAllarmi').style.display = "none";
}

google.maps.event.addDomListener(window, 'load', initialize);
