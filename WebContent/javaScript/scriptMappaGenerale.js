
function initialize()
{

	var objectVoloMap = document.getElementById('formBody:infoControllerToJs').value;
	objectVoloJsonMap = JSON.parse(objectVoloMap);
	
	var objectVoloInfo = document.getElementById('formBody:infoJsToController').value;
	objectVoloJsonInfo = JSON.parse(objectVoloInfo);
	
	bounds = new google.maps.LatLngBounds(new google.maps.LatLng(objectVoloJsonMap.minLat, objectVoloJsonMap.minLon), new google.maps.LatLng(objectVoloJsonMap.maxLat, objectVoloJsonMap.maxLon));//serve per ingandire in funzione delle coordinate massime
	map=new google.maps.Map(document.getElementById("googleMapGenerale"),mapProp);
	map.setCenter(bounds.getCenter());
	map.fitBounds(bounds);

	//define trip
	getTracciatoVolo(objectVoloJsonMap.tracciatoVolo);
	
	//set MARKER
	setMarkerPercorso(objectVoloJsonMap.tracciatoVolo);
}

google.maps.event.addDomListener(window, 'load', initialize);


