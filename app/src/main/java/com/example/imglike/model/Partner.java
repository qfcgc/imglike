package com.example.imglike.model;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Partner {
    @NonNull
    private String packageName;
    @NonNull
    private String name;
    private String description;
}
