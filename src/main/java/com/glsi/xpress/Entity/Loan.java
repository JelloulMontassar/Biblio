package com.glsi.xpress.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startingTime;
    private LocalDateTime finishingTime;
    private boolean isRenewed;
    private boolean isOverdue;
    private int renewCount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // isOverdue is true if the finishing time is before the current time
    public boolean isOverdue() {
        return finishingTime.isBefore(LocalDateTime.now());
    }
    // Method to renew the loan
    public void renewLoan() {
        if (this.renewCount < 3 && !isOverdue()) {
            this.renewCount++;
            this.finishingTime = this.finishingTime.plusDays(7); // Extending by 7 days

            if (this.renewCount == 1) {
                this.isRenewed = true;
            }
        } else {
            // Handle the case where the loan cannot be renewed
            throw new IllegalStateException("Loan cannot be renewed more than 3 times or is overdue.");
        }
    }

}
