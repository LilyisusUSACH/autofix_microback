package ajcc.autofix.micro2;

import ajcc.autofix.micro2.Entities.Reparation;
import ajcc.autofix.micro2.Repositories.ReparationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Micro2Application {

	public static void main(String[] args) {
		SpringApplication.run(Micro2Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ReparationRepo reparationRepo) {
		return args -> {

			if(reparationRepo.existsById(1L)) return;

			Reparation rep1 = new Reparation(1L,
					"Reparaciones del Sistema de Frenos",
					"Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.",
					120000, 120000, 180000, 220000
			);

			Reparation rep2 = new Reparation(2L,
					"Servicio del Sistema de Refrigeración",
					"Reparación o reemplazo de radiadores, bombas de agua, termostatos y mangueras, así como la solución de problemas de sobrecalentamiento.",
					130000, 130000, 190000, 230000
			);

			Reparation rep3 = new Reparation(3L,
					"Reparaciones del Motor",
					"Desde reparaciones menores como el reemplazo de bujías y cables, hasta reparaciones mayores como la reconstrucción del motor o la reparación de la junta de la culata.",
					350000, 450000, 700000, 800000
			);

			Reparation rep4 = new Reparation(4L,
					"Reparaciones de la Transmisión",
					"Incluyen la reparación o reemplazo de componentes de la transmisión manual o automática, cambios de líquido y solución de problemas de cambios de marcha.",
					210000, 210000, 300000, 300000
			);

			Reparation rep5 = new Reparation(5L,
					"Reparación del Sistema Eléctrico",
					"Solución de problemas y reparación de alternadores, arrancadores, baterías y sistemas de cableado, así como la reparación de componentes eléctricos como faros, intermitentes y sistemas de entretenimiento.",
					150000, 150000, 200000, 250000
			);

			Reparation rep6 = new Reparation(6L,
					"Reparaciones del Sistema de Escape",
					"Incluye el reemplazo del silenciador, tubos de escape, catalizador y la solución de problemas relacionados con las emisiones.",
					100000, 120000, 450000, 0
			);

			Reparation rep7 = new Reparation(7L,
					"Reparación de Neumáticos y Ruedas",
					"Reparación de pinchazos, reemplazo de neumáticos, alineación y balanceo de ruedas.",
					100000, 100000, 100000, 100000
			);

			Reparation rep8 = new Reparation(8L,
					"Reparaciones de la Suspensión y la Dirección",
					"Reemplazo de amortiguadores, brazos de control, rótulas y reparación del sistema de dirección asistida.",
					180000, 180000, 210000, 250000
			);

			Reparation rep9 = new Reparation(9L,
					"Reparación del Sistema de Aire Acondicionado y Calefacción",
					"Incluye la recarga de refrigerante, reparación o reemplazo del compresor, y solución de problemas del sistema de calefacción.",
					150000, 150000, 180000, 180000
			);

			Reparation rep10 = new Reparation(10L,
					"Reparaciones del Sistema de Combustible",
					"Limpieza o reemplazo de inyectores de combustible, reparación o reemplazo de la bomba de combustible y solución de problemas de suministro de combustible.",
					130000, 140000, 220000, 0
			);

			Reparation rep11 = new Reparation(11L,
					"Reparación y Reemplazo del Parabrisas y Cristales",
					"Reparación de pequeñas grietas en el parabrisas o reemplazo completo de parabrisas y ventanas dañadas.",
					80000, 80000, 80000, 80000
			);
			reparationRepo.saveAll(Arrays.asList(
					rep1, rep2, rep3, rep4, rep5, rep6, rep7, rep8, rep9, rep10, rep11
			));
		};
	}
}
