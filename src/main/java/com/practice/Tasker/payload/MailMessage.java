package com.practice.Tasker.payload;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailMessage {
    private String to;
    private String subject;
    private String text;
}
