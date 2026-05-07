package cl.sda1085.pagos.config;

import cl.sda1085.pagos.model.Pago;
import cl.sda1085.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor

public class DataInitializer implements CommandLineRunner {



        private final PagoRepository pagoRepository;

        @Override
        public void run(String... args) throws Exception {

            if (pagoRepository.count() > 0) {
                log.info("Base de datos de pagos ya contiene datos. Omitiendo inicialización.");
                return;
            }

            log.info("Iniciando la creación de transacciones de pago de prueba");

            Pago p1 = new Pago(
                    null,
                    1L,
                    6L,
                    new BigDecimal("175000.00"),
                    "COMPLETADO",
                    "TARJETA_CREDITO"
            );

            Pago p2 = new Pago(
                    null,
                    2L,
                    12L,
                    new BigDecimal("360000.00"),
                    "COMPLETADO",
                    "TRANSFERENCIA"
            );

            Pago p3 = new Pago(
                    null,
                    3L,
                    5L,
                    new BigDecimal("550000.00"),
                    "PENDIENTE",
                    "PAYPAL"
            );

            Pago p4 = new Pago(
                    null,
                    14L,
                    8L,
                    new BigDecimal("1000000.00"),
                    "FALLIDO",
                    "TARJETA_DEBITO"
            );

            Pago p5 = new Pago(
                    null,
                    14L,
                    8L,
                    new BigDecimal("1000000.00"),
                    "COMPLETADO",
                    "TRANSFERENCIA"
            );

            pagoRepository.saveAll(List.of(p1, p2, p3, p4, p5));

            log.info("Se han registrado 5 transacciones de pago sincronizadas lógicamente.");
        }
    }


