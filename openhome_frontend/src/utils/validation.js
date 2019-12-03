
var states = [
	"alabama",
	"alaska",
	"arizona",
	"arkansas",
	"california",
	"colorado",
	"connecticut",
	"delaware",
	"florida",
	"georgia",
	"hawaii",
	"idaho",
	"illinois indiana",
	"iowa",
	"kansas",
	"kentucky",
	"louisiana",
	"maine",
	"maryland",
	"massachusetts",
	"michigan",
	"minnesota",
	"mississippi",
	"missouri",
	"montana nebraska",
	"nevada",
	"new hampshire",
	"new jersey",
	"new mexico",
	"new york",
	"north carolina",
	"north dakota",
	"ohio",
	"oklahoma",
	"oregon",
	"pennsylvania rhode island",
	"south carolina",
	"south dakota",
	"tennessee",
	"texas",
	"utah",
	"vermont",
	"virginia",
	"washington",
	"west virginia",
	"wisconsin",
	"wyoming",
	"al",
    "ak",
    "as",
    "az",
    "ar",
    "ca",
    "co",
    "ct",
    "de",
    "dc",
    "fm",
    "fl",
    "ga",
    "gu",
    "hi",
    "id",
    "il",
    "in",
    "ia",
    "ks",
    "ky",
    "la",
    "me",
    "mh",
    "md",
    "ma",
    "mi",
    "mn",
    "ms",
    "mo",
    "mt",
    "ne",
    "nv",
    "nh",
    "nj",
    "nm",
    "ny",
    "nc",
    "nd",
    "mp",
    "oh",
    "ok",
    "or",
    "pw",
    "pa",
    "pr",
    "ri",
    "sc",
    "sd",
    "tn",
    "tx",
    "ut",
    "vt",
    "vi",
    "va",
    "wa",
    "wv",
    "wi",
    "wy"
	]


var cities = ["aberdeen", "abilene", "akron", "albany", "albuquerque", "alexandria", "allentown", "amarillo", "anaheim", "anchorage", "ann arbor", "antioch", "apple valley", "appleton", "arlington", "arvada", "asheville", "athens", "atlanta",
	"atlantic city", "augusta", "aurora", "austin", "bakersfield", "baltimore", "barnstable", "baton rouge", "beaumont", "bel air", "bellevue", "berkeley", "bethlehem", "billings", "birmingham", "bloomington", "boise", "boise city", "bonita springs",
	"boston", "boulder", "bradenton", "bremerton", "bridgeport", "brighton", "brownsville", "bryan", "buffalo", "burbank", "burlington", "cambridge", "canton", "cape coral", "carrollton", "cary", "cathedral city", "cedar rapids", "champaign", "chandler",
	"charleston", "charlotte", "chattanooga", "chesapeake", "chicago", "chula vista", "cincinnati", "clarke county", "clarksville", "clearwater", "cleveland", "college station", "colorado springs", "columbia", "columbus", "concord", "coral springs", "corona",
	"corpus christi", "costa mesa", "dallas", "daly city", "danbury", "davenport", "davidson county", "dayton", "daytona beach", "deltona", "denton", "denver", "des moines", "detroit", "downey", "duluth", "durham", "el monte", "el paso", "elizabeth",
	 "elk grove", "elkhart", "erie", "escondido", "eugene", "evansville", "fairfield", "fargo", "fayetteville", "fitchburg", "flint", "fontana", "fort collins", "fort lauderdale", "fort smith", "fort walton beach", "fort wayne", "fort worth", "frederick",
	 "fremont", "fresno", "fullerton", "gainesville", "garden grove", "garland", "gastonia", "gilbert", "glendale", "grand prairie", "grand rapids", "grayslake", "green bay", "greenbay", "greensboro", "greenville", "gulfport-biloxi", "hagerstown", "hampton",
	  "harlingen", "harrisburg", "hartford", "havre de grace", "hayward", "hemet", "henderson", "hesperia", "hialeah", "hickory", "high point", "hollywood", "honolulu", "houma", "houston", "howell", "huntington", "huntington beach", "huntsville", "independence",
		 "indianapolis", "inglewood", "irvine", "irving", "jackson", "jacksonville", "jefferson", "jersey city", "johnson city", "joliet", "kailua", "kalamazoo", "kaneohe", "kansas city", "kennewick", "kenosha", "killeen", "kissimmee", "knoxville", "lacey",
		  "lafayette", "lake charles", "lakeland", "lakewood", "lancaster", "lansing", "laredo", "las cruces", "las vegas", "layton", "leominster", "lewisville", "lexington", "lincoln", "little rock", "long beach", "lorain", "los angeles", "louisville", "lowell",
			 "lubbock", "macon", "madison", "manchester", "marina", "marysville", "mcallen", "mchenry", "medford", "melbourne", "memphis", "merced", "mesa", "mesquite", "miami", "milwaukee", "minneapolis", "miramar", "mission viejo", "mobile",
			  "modesto", "monroe","monterey", "montgomery", "moreno valley", "murfreesboro", "murrieta", "muskegon", "myrtle beach", "naperville", "naples", "nashua", "nashville", "new bedford", "new haven", "new london", "new orleans", "new york", "new york city", "newark",
				 "newburgh", "newport news", "norfolk", "normal", "norman", "north charleston", "north las vegas", "north port", "norwalk", "norwich", "oakland", "ocala", "oceanside", "odessa", "ogden", "oklahoma city", "olathe", "olympia", "omaha", "ontario",
				 "orange", "orem", "orlando", "overland park", "oxnard", "palm bay", "palm springs", "palmdale", "panama city", "pasadena", "paterson", "pembroke pines", "pensacola", "peoria", "philadelphia", "phoenix", "pittsburgh", "plano", "pomona", "pompano beach",
				 "port arthur", "port orange", "port saint lucie", "port st. lucie", "portland", "portsmouth", "poughkeepsie", "providence", "provo", "pueblo", "punta gorda", "racine", "raleigh", "rancho cucamonga", "reading", "redding", "reno", "richland", "richmond", "richmond county",
				  "riverside", "roanoke", "rochester", "rockford", "roseville", "round lake beach", "sacramento", "saginaw", "saint louis", "saint paul", "saint petersburg", "salem", "salinas", "salt lake city", "san antonio", "san bernardino", "san buenaventura", "san diego", "san francisco",
					 "san jose", "santa ana", "santa barbara", "santa clara", "santa clarita", "santa cruz", "santa maria", "santa rosa", "sarasota", "savannah", "scottsdale", "scranton", "seaside", "seattle", "sebastian", "shreveport", "simi valley", "sioux city", "sioux falls", "south bend",
					  "south lyon", "spartanburg", "spokane", "springdale", "springfield", "st. louis", "st. paul", "st. petersburg", "stamford", "sterling heights", "stockton", "sunnyvale", "syracuse", "tacoma", "tallahassee", "tampa", "temecula", "tempe", "thornton", "thousand oaks",
						 "toledo", "topeka", "torrance", "trenton", "tucson", "tulsa", "tuscaloosa", "tyler", "utica", "vallejo", "vancouver", "vero beach", "victorville", "virginia beach", "visalia", "waco", "warren", "washington", "waterbury", "waterloo",
						  "west covina", "west valley city", "westminster", "wichita", "wilmington", "winston", "winter haven","worcester", "yakima", "yonkers", "york", "youngstown"];

export function cityValidity(city){
		if(typeof city === "string"){
			var result= cities.indexOf(city.toLowerCase()) > -1;
			if(result){
				return true;
			}else{
				alert("Please enter valid US city");
				return false;
			}
		}else{
			return false;
		}

	}
export function stateValidity(state){
	if(typeof state === "string"){
		const result = states.indexOf(state.toLowerCase()) > -1;
    if(result){
      return true;
    }
    else{
      alert("Please enter valid US state");
			return false;
    }
	}
	return false;
}

export function zipCodeValidity(zipcode) {
       var regExpression = /^(\d{5})?$/;
       var pattern = new RegExp(regExpression);
       var result= pattern.test(zipcode);
       if(result){
         return result;
       }else{
         alert("Zip code should be a 5 digit number");
       }
}

export function phoneNumberValidity (phoneNumber) {
       var regExpression = /^(\d{10})?$/;
       var pattern = new RegExp(regExpression);
       var result= pattern.test(phoneNumber);
       if(result){
         return result;
       }
       else{
         alert("Phone number should be 10 digit number");
				 return false;
       }
}

export function dateValidity(date, field){
  if(date && date.length>0){
    return true;
  }
  alert("Field DATE is missing or invalid !!!");
  return false;
}

export function emailValidity (email) {
	if(email && email.length >0){
		const regExpression = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    var pattern = new RegExp(regExpression);
    var result= pattern.test(email);
    if(result){
      return result;
    }else{
      alert("Please enter valid Email");
			return false
    }
	}else{
		alert("Field EMAIL is missing or not valid !!!");
	  return false;
	}
}

export function startEndDateValidity (startDate , endDate) {
       if ((Date.parse(endDate) > Date.parse(startDate))) {
           return true;
       }else{
        alert("Start date should not be greater or equal to End date");
        return false;
       }
}

 export function fieldValidity (field,data) {
        if(data && data.length>0){
          return true;
        }
        alert("Required field ["+field+"] is missing or not valid !!!");
        return false;
    }

export function nameValidity (name) {
	if(name && name.length >0){
       var regExpression= /^[a-zA-Z ]{2,30}$/
       var pattern = new RegExp(regExpression);
       var result= pattern.test(name);
			 if(result){
         return result;
       }
       else{
         alert("Name should be more than 2 characters and less than 30 characters and only alphabets");
       }
		 }else{
			 alert("Either field first name or last name is missing !!!");
			 return false;
		 }
}

export function numericValidation(value, field){
    var regExpression = /^\d+$/;
    var pattern = new RegExp(regExpression);
    var result = pattern.test(value);
    if(!result){
      alert("Required field ["+field+"] should be numeric !!!");
    }
    return result;
}

export function cvvNumberValidity (cvvNumber) {
	if(cvvNumber && cvvNumber.length >0){
       var regExpression = /^(\d{3})?$/;
       var pattern = new RegExp(regExpression);
       var result= pattern.test(cvvNumber);
			 if(result){
         return result;
       }
       else{
         alert("User entered incorrect CVV..!");
       }
		 }else{
			 alert("CVV detail is missing.!! Please enter CVV");
			 return false;
		 }
}

export function creditCardValidity (cardNumber) {
			if(cardNumber && cardNumber.length >0){
				var regExpression = /^(\d{16})?$/;
        var pattern = new RegExp(regExpression);
        var result= pattern.test(cardNumber);
 			 if(result){
          return result;
        }
        else{
          alert("User entered Invalid Credit Card Number");
					return false;
        }
			}else{
				alert("Required field credit card detail is missing !!!");
			  return false;
			}
}

export function creditCardTypeValidity (cardType) {
			if(cardType && cardType.length >0 && (cardType.toLowerCase() == "debit" || cardType.toLowerCase() == "credit")){
				return true;
			}else{
				alert("Only debit or credit card type is allowed !!!");
			  return false;
			}
}
