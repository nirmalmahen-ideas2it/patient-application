package com.ideas2it.training.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Generic DTO for paginated responses.
 * <p>
 * This class is used to encapsulate paginated data for API responses. It includes
 * the list of items, total number of elements, current page, and page size.
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 *
 * @param <T> The type of items in the paginated response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<T> {

    private List<T> items;
    private long totalElements;
    private int page;
    private int size;
}