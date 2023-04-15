package org.restassured;

import static io.restassured.RestAssured.given;

public class GraphQLScript {
    public static void main(String[] args) {
        int characterId = 1312;
        int locationId =  1731;
        String response = given().log().all().header("Content-Type", "application/json").body("{\"query\":\"query($characterId : Int!, $locationId: Int!, $episodeId: Int!, $name: String!, $episodeName : String!)\\n{\\n  character(characterId: $characterId){\\n    name\\n    gender\\n    status\\n    type\\n    image\\n    id\\n  },\\n  location(locationId: $locationId){\\n    name\\n    dimension\\n  },\\n  episode(episodeId: $episodeId){\\n    name\\n    air_date\\n    episode\\n  },\\n  characters(filters: {name: $name}){\\n    info{\\n      count\\n    }\\n  },\\n  episodes(filters:{episode: $episodeName})" +
                        "{\\n    result{\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\",\"variables\":{\"characterId\":" + characterId +
                        ",\"locationId\":" + locationId + ",\"episodeId\":999,\"name\":\"Kevin Costner\",\"episodeName\":\"hulu\"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(response);
    }
}
