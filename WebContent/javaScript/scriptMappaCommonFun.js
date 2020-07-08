 

//---var ApiMaps---
var minZoomVar; 
var map;
var mapProp = {mapTypeId:google.maps.MapTypeId.ROADMAP};
var bounds;
var flightPathPercorso;
var voloCircleOptionsArray = new Array();
var image = {
	    url: '../../resources/images/others/marker.png',
	    anchor: new google.maps.Point(7, 7)
	  };

//---var mappe---- 
var objectVoloJsonMap;
var objectVoloJsonInfo;
var tracciatoVoloJs = new Array(); 
var grigliaVoloJs = new Array();
var flightPathGrigliaVoloJs = new Array(); 
var flightPathGrigliaVoloJs;
var marker;
var voloCircle;
var singleMarker = null;
var voloCircleRaggioOptions = null;
var objectSinglePointJsonMarker;

//---var vario----
var infoObj;
var stringTmpFromJsToController;
var singleElementoSelect;
var chiavePrimariaCerchioCoordinata = -1;
var chiudiCerchio = false;

//********************************************************
//funzione utilizzata per stampare il persorso del volo***
//********************************************************
function getTracciatoVolo(tracciatoVolo){	
	for (var j = 0; j<tracciatoVolo.length; j++){
		tracciatoVoloJs[j] = new google.maps.LatLng(tracciatoVolo[j].latitudine,tracciatoVolo[j].longitudine);
	}
	
	singleMarker = new google.maps.Marker({
	    position: tracciatoVoloJs[0],
	    map: map
	});
	singleMarker.setMap(map);

	flightPathPercorso = new google.maps.Polyline({
		  path:tracciatoVoloJs,
		  strokeColor:"#000000",
		  strokeOpacity:0.4,
		  strokeWeight:1
		  });		
	flightPathPercorso.setMap(map);
}


//****************************************************
//funzione utilizzata per add marker e info associate*
//****************************************************
function setMarkerPercorso(tracciatoVolo){
	
	for (var i = 0; i < tracciatoVolo.length; i++) {		  
		
		//setto le info relative alle coordinate di output:
		objectVoloJsonInfo.infoCoordinateLtLn = tracciatoVolo[i];
		
	    marker = new google.maps.Marker({
	      position: new google.maps.LatLng(tracciatoVolo[i].latitudine,tracciatoVolo[i].longitudine),
	      icon: image,
	      map: map,
	      objInfo: objectVoloJsonInfo.infoCoordinateLtLn
	    });
	    
	    attachMessageCoordinata(marker);
	  }		
}
function attachMessageCoordinata(marker) {
	google.maps.event.addListener(marker, 'click', function() {
		
		objectVoloJsonInfo.infoQuadrato = null;
		objectVoloJsonInfo.infoCoordinateLtLn = marker.objInfo; 
		
		stringTmpFromJsToController = JSON.stringify(objectVoloJsonInfo); //converte un oggetto in una string json per passarlo al controller
		document.getElementById('formBody:infoJsToController').value = stringTmpFromJsToController;
		infoJsToControllerFun();
	});
}


//**********************************************************
//funzione utilizzata per add quadrato info associate***
//**********************************************************
function getGrigliaVolo(grigliaMappa){
	//L ordine di ogni quadrato deve essere nordOvest, sudOvest, sudEst, nordEst
	
	for (var j = 0; j<grigliaMappa.length; j++){	
		var nordOvest=new google.maps.LatLng(grigliaMappa[j].nordOvest.latitudine,grigliaMappa[j].nordOvest.longitudine);
		var sudOvest=new google.maps.LatLng(grigliaMappa[j].sudOvest.latitudine,grigliaMappa[j].sudOvest.longitudine);
		var sudEst=new google.maps.LatLng(grigliaMappa[j].sudEst.latitudine,grigliaMappa[j].sudEst.longitudine);
		var nordEst=new google.maps.LatLng(grigliaMappa[j].nordEst.latitudine,grigliaMappa[j].nordEst.longitudine);
				
		//setto le info relative al quadrato di output:
		objectVoloJsonInfo.infoQuadrato = grigliaMappa[j];		
		
		grigliaVoloJs[j] = [nordOvest, sudOvest, sudEst, nordEst];		
		
		flightPathGrigliaVoloJs=new google.maps.Polygon({
			  path:grigliaVoloJs[j],
			  strokeColor:"#000000",
			  strokeOpacity:2,
			  strokeWeight:0.5,
			  fillColor: grigliaMappa[j].color,
			  fillOpacity:0.2, 
			  objInfo: objectVoloJsonInfo.infoQuadrato
			  });
	
		flightPathGrigliaVoloJs.setMap(map);
		
		attachMessageQuadrato(flightPathGrigliaVoloJs);
	}
}
function attachMessageQuadrato(flightPathGrigliaVoloJs) {	
		google.maps.event.addListener(flightPathGrigliaVoloJs, 'click', function()  {
			
			objectVoloJsonInfo.infoCoordinateLtLn = null;
			objectVoloJsonInfo.infoQuadrato =  flightPathGrigliaVoloJs.objInfo;
			
			stringTmpFromJsToController = JSON.stringify(objectVoloJsonInfo); //converte un oggetto in una string json per passarlo al controller
			document.getElementById('formBody:infoJsToController').value = stringTmpFromJsToController;
			infoJsToControllerFun();
		});
}


//**********************************************************
//funzione utilizzata per add circle ad ogni coordinata***
//**********************************************************
function getCircleCoordinata(tracciatoVolo, singleElementoSelect){
	
	//clean the map from other coloured circle 
	for (var i = 0; i < voloCircleOptionsArray.length; i++) {
		voloCircleOptionsArray[i].setMap(null);
	}
	
	var colourStamp;
	for (var i = 0; i < tracciatoVolo.length; i++) {
		objectVoloJsonInfo.infoCoordinateLtLn = tracciatoVolo[i];
		
		switch (singleElementoSelect)
		{
		  case 'Potassio':
		  			{colourStamp = tracciatoVolo[i].colourPotassio;}
		      		break;
		  case 'Uranio':
		  			{colourStamp = tracciatoVolo[i].colourUranio;}
		            break;
		  case 'Torio': 
		  			{colourStamp = tracciatoVolo[i].colourTorio;}
		            break;
		  case 'Cesio': 
		  			{colourStamp = tracciatoVolo[i].colourCesio;};
		            break;
		  case 'eTh/eU': 
					{colourStamp = tracciatoVolo[i].colourTorioUranio;};
					break;
		  case 'k/eU': 
					{colourStamp = tracciatoVolo[i].colourPotassioUranio;};
					break;
		  case 'ChiSQ': 
					{colourStamp = tracciatoVolo[i].colourChiQuadro;};
					break;
		  case 'Allarmi': 
					{colourStamp = tracciatoVolo[i].colourAllarmi;};
					break;
		}
		
		voloCircleOptions = new google.maps.Circle({
	      //strokeColor: '#FF0000',
	      strokeOpacity: 0.0,
	      strokeWeight: 2,
	      fillColor: colourStamp,
	      fillOpacity: 0.6,
	      map: map,
	      center: new google.maps.LatLng(tracciatoVolo[i].latitudine,tracciatoVolo[i].longitudine),
	      radius: 10,
	      objInfo: objectVoloJsonInfo.infoCoordinateLtLn
	    });
		voloCircleOptionsArray[i] = voloCircleOptions;
	    // Add the circle for this coordinate to the map.
		voloCircleOptions.setMap(map);
	    attachMessageCircle(voloCircleOptions);
	}
  }
function attachMessageCircle(voloCircleOptions) {
	google.maps.event.addListener(voloCircleOptions, 'click', function() {
		objectVoloJsonInfo.infoQuadrato = null;
		objectVoloJsonInfo.infoCoordinateLtLn = voloCircleOptions.objInfo; 
		
		stringTmpFromJsToController = JSON.stringify(objectVoloJsonInfo); //converte un oggetto in una string json per passarlo al controller
		document.getElementById('formBody:infoJsToController').value = stringTmpFromJsToController;
		infoJsToControllerFun();
	});
}

//*****************************************************************
//funzione utilizzata per il singolo marker al click sul grafico***
//*****************************************************************
function singlePointMarker() {
	var objectSinglePointMarker = document.getElementById('formBody:infoControllerToJsSinglePointMarker').value;	
	objectSinglePointJsonMarker = JSON.parse(objectSinglePointMarker);
	
	var myLatlng = new google.maps.LatLng(objectSinglePointJsonMarker.latitudine, objectSinglePointJsonMarker.longitudine);
	if(singleMarker!=null){
		singleMarker.setMap(null);
	}
	resetRaggioCircle();
	
	map.setCenter(myLatlng);
	
	singleMarker = new google.maps.Marker({
	    position: myLatlng,
	    map: map
	});
}

//*****************************************************************
//**funzione utilizzata per il reset del raggio cirocolare*********
//*****************************************************************
function resetRaggioCircle(){
	if(voloCircleRaggioOptions!=null){
		voloCircleRaggioOptions.setMap(null);
	}
}

//*****************************************************************
//funzione utilizzata per il change colour backgroud al click******
//*****************************************************************
function changeColourBackground() {
	var typeGeneraleController = document.getElementById('formBody:typeGeneraleController').value;
	var typeConcentrazioniController = document.getElementById('formBody:typeConcentrazioniController').value;
	var typeAllarmeController = document.getElementById('formBody:typeAllarmeController').value;
	
	if(typeGeneraleController == "true"){
		document.getElementById("formBody:mapGeneraleButton").style.backgroundColor="red";
	}
	
	if(typeConcentrazioniController == "true"){
		document.getElementById("formBody:mapConcentrazioniButton").style.backgroundColor="red";
	}
	
	if(typeAllarmeController == "true"){
		document.getElementById("formBody:mapAllarmiButton").style.backgroundColor="red";	
	}
}
