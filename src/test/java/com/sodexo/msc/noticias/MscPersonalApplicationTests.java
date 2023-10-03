package com.sodexo.msc.noticias;

import com.sodexo.msc.noticias.controller.NoticiasController;
import com.sodexo.msc.noticias.entity.NoticiaFavorita;
import com.sodexo.msc.noticias.models.response.NoticiasExternas;
import com.sodexo.msc.noticias.models.response.Respuesta;
import com.sodexo.msc.noticias.service.NoticiasService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
@WebMvcTest(NoticiasController.class)
class MscPersonalApplicationTests {


		@Autowired
		private MockMvc mockMvc;

		@MockBean
		private NoticiasService noticiasService;

		@MockBean
		private RestTemplate restTemplate;

		@InjectMocks
		private NoticiasController noticiasController;

		@Test
		void testListarNoticiasExternas() throws Exception {
			// Mocking the noticiasService
			Mockito.when(noticiasController.listarNoticiasExternas(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
					.thenReturn("Sample response from external API");

			mockMvc.perform(MockMvcRequestBuilders.get("/externas")
							.param("search", "someSearch")
							.param("offset", "5")
							.param("ordering", "published_at"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().string("Sample response from external API"));
		}

		@Test
		void testGuardarFavorita() throws Exception {
			// Mocking the noticiasService
			Mockito.when(noticiasController.guardarFavorita(Mockito.any(NoticiasExternas.class)))
					.thenReturn(new Respuesta(true, "Noticia guardada con éxito."));

			mockMvc.perform(MockMvcRequestBuilders.post("/guardarFavorita")
							.contentType(MediaType.APPLICATION_JSON)
							.content("{ \"id\": \"123\", \"summary\": \"Some summary\", \"title\": \"Some title\", \"featured\": true, \"url\": \"http://example.com\", \"image_url\": \"http://example.com/image.jpg\", \"news_site\": \"Example News\", \"published_at\": \"2023-09-28T10:00:00Z\", \"updated_at\": \"2023-09-28T10:00:00Z\" }"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.valido").value(true))
					.andExpect(MockMvcResultMatchers.jsonPath("$.mensaje").value("Noticia guardada con éxito."));
		}

		@Test
		void testListarNoticiasFavoritas() throws Exception {
			// Mocking the noticiasService
			List<NoticiaFavorita> favoritas = new ArrayList<>();
			// Add some sample noticias to the list
			favoritas.add(new NoticiaFavorita(/* Initialize with some values */));

			Mockito.when(noticiasController.listarNoticiasFavoritas(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
					.thenReturn(new Respuesta(true, "", favoritas));

			mockMvc.perform(MockMvcRequestBuilders.get("/favoritas")
							.param("titulo", "someTitle")
							.param("offset", "5")
							.param("ordering", "desc"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").exists()) // Assuming there's an 'id' field in NoticiaFavorita
					.andExpect(MockMvcResultMatchers.jsonPath("$.mensaje").doesNotExist());
		}

		@Test
		void testContarFavoritas() throws Exception {
			// Mocking the noticiasService
			Mockito.when(noticiasController.contarFavoritas(Mockito.anyString())).thenReturn(10);

			mockMvc.perform(MockMvcRequestBuilders.get("/contarFavoritas")
							.param("titulo", "someTitle"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().string("10"));
		}

		@Test
		void testEliminarFavorito() throws Exception {
			// Mocking the noticiasService
			Mockito.when(noticiasController.eliminarFavorito(Mockito.anyLong())).thenReturn(new Respuesta(true,""));

			mockMvc.perform(MockMvcRequestBuilders.delete("/1"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.valido").value(true));
		}
	}