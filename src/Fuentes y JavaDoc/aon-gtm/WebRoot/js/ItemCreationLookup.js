var ITEM_CREA_SERVLET = "/field.itemCreationLookup";

function findItemCreationLookup(evt, ctx, pojo, ids) {
    // equalize W3C/IE event models to get event object
    evt = (evt) ? evt : ((window.event) ? window.event : null);
    if (evt) {
        // equalize W3C/IE models to get event target reference
        var elem = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
        if (elem) {
            try {
				url = location.protocol + '://' + location.host + ctx + ITEM_CREA_SERVLET;
				var fullURL = url + '?name=' + elem.name + '&value=' + elem.value + '&pojo=' + pojo;
                if ( ids ) {
                    fullURL += '&ids=' + ids;
                } 
				sendLookup( fullURL );
            } catch(e) {
                var msg = (typeof e == "string") ? e : ((e.message) ? e.message : "Unknown Error");
                alert("Unable to get XML data:\n" + msg);
                return;
            }
        }
    }
}

function findItemCreationLookupAndSubmit(formSubmit, evt, ctx, pojo,ids) {
	findItemCreationLookup(evt,ctx,pojo,ids);
	mustSubmitForm = true;
	formToSubmit = formSubmit;
}