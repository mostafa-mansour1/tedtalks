/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.deserialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.io.tedtalks.entity.Talks;
import com.io.tedtalks.exception.InternalServerError;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author mostafa
 */
public class TalksDeserializer extends JsonDeserializer<Talks> {

    @Override
    public Talks deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        // if user add wrong format of integer, it will convert to 0
        Long views = Long.parseLong("0");
        Long likes = Long.parseLong("0");
        Date date = null;
        String link = "";

        if (node.get("views") != null) {
            views = node.get("views").asLong(0);
                    if(views < 0) views =Long.valueOf("0");
        }
        if (node.get("likes") != null) {
            likes = node.get("likes").asLong(0);
                    if(likes < 0) likes =Long.valueOf("0");
        }

        if (link != null) {
            link = node.get("link").asText();
        }

        if (node.get("date") == null) {
            throw new InternalServerError("date is mandatory");
        }
        try {
            // we can add some logics her to allow any date format
            String dateText = node.get("date").asText();
            LocalDate parseDate = LocalDate.parse(dateText);
            date = Date.valueOf(parseDate);
        } catch (Exception e) {
            date = null;
        }

        if (node.get("title") == null) {
            throw new InternalServerError("title is mandatory");
        }
        String title = node.get("title").asText();

        if (node.get("author") == null) {
            throw new InternalServerError("author is mandatory");
        }
        String author = node.get("author").asText();

        return new Talks(
                title,
                author,
                date,
                views,
                likes,
                link
        );
    }

}
