
function initialize()
{

	var objectVoloMap = document.getElementById('formBody:infoControllerToJs').value;
	objectVoloJsonMap = JSON.parse(objectVoloMap);
	
	singleElementoSelect = document.getElementById('formBody:infoControllerToJsSingleElemento').value;
	
	var objectVoloInfo = document.getElementById('formBody:infoJsToController').value;
	objectVoloJsonInfo = JSON.parse(objectVoloInfo);
	
	bounds = new google.maps.LatLngBounds(new google.maps.LatLng(objectVoloJsonMap.minLat, objectVoloJsonMap.minLon), new google.maps.LatLng(objectVoloJsonMap.maxLat, objectVoloJsonMap.maxLon));//serve per ingandire in funzione delle coordinate massime
	map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
	map.setCenter(bounds.getCenter());
	map.fitBounds(bounds);

	//define trip
	getTracciatoVolo(objectVoloJsonMap.tracciatoVolo);
//	
//	//set MARKER
//	setMarkerPercorso(objectVoloJsonMap.tracciatoVolo);
//	
//	//defin grid
//	getGrigliaVolo(objectVoloJsonMap.grigliaMappa);
	
	getCircleCoordinata(objectVoloJsonMap.tracciatoVolo, singleElementoSelect);
}


function gestioneRaggioCerchioCoordinata(){
	if(chiavePrimariaCerchioCoordinata != objectSinglePointJsonMarker.idVoloRigaKP){
//		if(chiudiCerchio){
//			voloCircleRaggioOptions.setMap(null);
//			chiudiCerchio = false;
//		}
//		chiavePrimariaCerchioCoordinata = -1;
//	}else{	
		resetRaggioCircle();
		chiavePrimariaCerchioCoordinata = objectSinglePointJsonMarker.idVoloRigaKP;
		var colourStamp;	
		switch (singleElementoSelect)
		{
		  case 'Potassio':
		  			{colourStamp = objectSinglePointJsonMarker.colourPotassio;}
		      		break;
		  case 'Uranio':
		  			{colourStamp = objectSinglePointJsonMarker.colourUranio;}
		            break;
		  case 'Torio': 
		  			{colourStamp = objectSinglePointJsonMarker.colourTorio;}
		            break;
		  case 'Cesio': 
		  			{colourStamp = objectSinglePointJsonMarker.colourCesio;};
		            break;
		  case 'eTh/eU': 
					{colourStamp = objectSinglePointJsonMarker.colourTorioUranio;};
					break;
		  case 'k/eU': 
					{colourStamp = objectSinglePointJsonMarker.colourPotassioUranio;};
					break;
		  case 'ChiSQ': 
					{colourStamp = objectSinglePointJsonMarker.colourChiQuadro;};
					break;
		}
			
		voloCircleRaggioOptions = new google.maps.Circle({
		      strokeOpacity: 0.0,
		      strokeWeight: 2,
		      fillColor: colourStamp,
		      fillOpacity: 0.6,
		      map: map,
		      center: new google.maps.LatLng(objectSinglePointJsonMarker.latitudine, objectSinglePointJsonMarker.longitudine),
		      radius: objectSinglePointJsonMarker.raggioCerhioCoordinata,
		    });
		// Add the circle for this coordinate to the map.
	voloCircleRaggioOptions.setMap(map);
	}
}

function changeColourElement() {
	    
	singleElementoSelect = document.getElementById('formBody:infoControllerToJsSingleElemento').value;
    getCircleCoordinata(objectVoloJsonMap.tracciatoVolo, singleElementoSelect);
    setTimeout(stopWaitLoadElemento, 6000);   
   
}  
google.maps.event.addDomListener(window, 'load', initialize);

function waitLoadElemento(){
	var div = document.getElementById('bloccoLoadElemento');
	div.style.display = "block";
}

function stopWaitLoadElemento(){
	 document.getElementById('bloccoLoadElemento').style.display = "none";
}

function showDetail() {
	alert('showDetail');
}

function ShowImage()
{
    var div = document.getElementById('popupRangeConcentrazioni');
    div.style.display = "block";
}

function HideImage()
{
    document.getElementById('popupRangeConcentrazioni').style.display = "none";
}