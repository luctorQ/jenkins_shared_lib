package com.hastingsdirect.ep.scripts

import com.hastingsdirect.ep.ExtendedProperty;
import com.hastingsdirect.sql.RepositoryBuilds;

def repo=new RepositoryBuilds()
def builds = repo.buildsOnePromoted()

def jsonEditorOptions = ExtendedProperty.fromJson(/{
	   disable_edit_json: true,
	   disable_properties: true,
	   no_additional_properties: true,
	   disable_collapse: true,
	   disable_array_add: true,
	   disable_array_delete: true,
	   disable_array_reorder: true,
	   theme: "bootstrap2",
	   iconlib:"fontawesome4",
	   schema: {
	"title": "Builds",
	"type": "array",
	"format":"table",
	"items": {
	  "type": "object",
	  "properties": {
		"select":{
		  "title":"Select",
		  "type":"boolean",
		  "format":"checkbox",
		  "propertyOrder":1
		},
		"cijenkinsbuildid": {
		  "title":"CI Build",
		  "type": "number",
		  "readOnly":true,
		  "propertyOrder":2
		},
		"absvnrevisionnumber": {
		  "title":"CM svn",
		  "type": "number",
		  "readOnly":true,
		  "propertyOrder":3
		},
		"abjenkinsbuildid": {
		  "title":"CM build",
		  "type": "number",
		  "readOnly":true,
		  "propertyOrder":4
		},
		"pcsvnrevisionnumber": {
		  "title":"PC svn",
		  "type": "number",
		  "readOnly":true,
		  "propertyOrder":5
		},
		"pcjenkinsbuildid": {
		  "title":"PC build",
		  "type": "number",
		  "readOnly":true,
		  "propertyOrder":6
		},
		"bcsvnrevisionnumber": {
		  "title":"BC svn",
		  "type": "number",
		  "readOnly":true,
		  "propertyOrder":7
		},
		"bcjenkinsbuildid": {
			"title":"BC build",
			"type": "number",
			"readOnly":true,
			"propertyOrder":8
		},
		"ccsvnrevisionnumber": {
		  "title":"CC svn",
		  "type": "number",
		  "readOnly":true,
		  "propertyOrder":9
		},
		"ccjenkinsbuildid": {
			"title":"PC build",
			"type": "number",
			"readOnly":true,
			"propertyOrder":10
		}
	  }
	}
},
	startval: ${ExtendedProperty.toJson(builds)}
}/);
return jsonEditorOptions