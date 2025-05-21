package fliduan.leasecompany.repository;

import fliduan.leasecompany.domain.Car;
import fliduan.leasecompany.domain.Interest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Integer> {
    /**
     * Retrieve interest rates with the startDate before the given startDate
     * @param startDate
     * @param sort
     * @return {@link List<Interest>}
     */
    Interest findTop1ByStartDateIsBefore(LocalDate startDate, Sort sort);
}
