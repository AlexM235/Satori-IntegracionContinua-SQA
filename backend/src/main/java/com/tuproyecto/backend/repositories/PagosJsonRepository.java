package com.tuproyecto.backend.repositories;

import com.tuproyecto.backend.models.Pago;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PagosJsonRepository extends JsonRepository<Pago> {

    public PagosJsonRepository() {
        super("pagos.json", Pago.class);
    }

    public List<Pago> findAll() {
        return loadData();
    }

    public void saveAll(List<Pago> pagos) {
        saveData(pagos);
    }
}
