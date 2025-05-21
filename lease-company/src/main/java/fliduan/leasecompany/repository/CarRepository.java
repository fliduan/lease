package fliduan.leasecompany.repository;

import fliduan.leasecompany.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    /**
     * Retrieve cars information based on make, (optional) model, (optional) version.
     * @param make
     * @param model
     * @param version
     * @return
     */
    @Query("select c from Car c where c.make=:make and (:model is null or c.model=:model) and (:version is null or c.version=:version)")
    List<Car> retrieveCarsByMakeAndModelAndVersion(@Param("make") String make, @Param("model") String model, @Param("version") String version);
}
