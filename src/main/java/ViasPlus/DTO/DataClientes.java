package ViasPlus.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DataClientes(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "Debe ser un correo electrónico válido")
        String correo,

        @NotBlank(message = "La dirección no puede estar vacía")
        @Size(min = 5, max = 100, message = "La dirección debe tener entre 5 y 100 caracteres")
        String direccion,

        @NotBlank(message = "El teléfono no puede estar vacío")
        @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos")
        String telefono
) {}

