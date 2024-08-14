package com.personal.project.angi.util;

import co.elastic.clients.elasticsearch._types.SortOrder;
import com.personal.project.angi.constant.FileConstant;
import com.personal.project.angi.model.dto.request.FilterRequest;
import com.personal.project.angi.model.dto.request.SortRequest;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class Util {
    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public String generateFileDirectory(String... arg) {
        return String.join(FileConstant.DIRECTORY_DIVIDE, arg);
    }

    public List<SortRequest> parseSortRequest(String sortString) {
        List<SortRequest> sortRequestList = new ArrayList<>();

        String[] fields = sortString.split(",");
        for (String field : fields) {
            SortRequest sortRequest = new SortRequest();
            if (field.startsWith("+")) {
                sortRequest.setSortField(field.substring(1));
                sortRequest.setSortDirection(SortOrder.Asc);
            } else if (field.startsWith("-")) {
                sortRequest.setSortField(field.substring(1));
                sortRequest.setSortDirection(SortOrder.Desc);
            } else {
                sortRequest.setSortField(field.substring(1));
                sortRequest.setSortDirection(SortOrder.Asc); // default
            }
            sortRequestList.add(sortRequest);
        }
        return sortRequestList;
    }

    public List<FilterRequest> parseFilterRequest(String filterString) {
        List<FilterRequest> filterRequestList = new ArrayList<>();
        String[] filters = filterString.split("AND");
        for (String filter : filters) {
            FilterRequest filterRequest = new FilterRequest();
            String[] parts = filter.split("=");
            String fieldWithOp = parts[0];
            String value = parts[1];

            int operationStart = fieldWithOp.indexOf('(');
            int operationEnd = fieldWithOp.indexOf(')');

            if (operationStart != -1 && operationEnd != -1) { //check if have LHS bracket
                filterRequest.setFilterField(fieldWithOp.substring(0, operationStart));
                filterRequest.setFilterOperations(fieldWithOp.substring(operationStart + 1, operationEnd));
            } else {
                filterRequest.setFilterField(fieldWithOp);
                filterRequest.setFilterOperations("eq"); // Default to equals
            }

            filterRequest.setFliterValue(value);
            filterRequestList.add(filterRequest);
        }
        return filterRequestList;
    }
}
