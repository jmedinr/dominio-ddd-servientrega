package co.com.sofkau.entrenamiento.curso.clientes;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofkau.entrenamiento.curso.clientes.entities.Destinatario;
import co.com.sofkau.entrenamiento.curso.clientes.entities.Remitente;
import co.com.sofkau.entrenamiento.curso.clientes.events.ClienteCreado;
import co.com.sofkau.entrenamiento.curso.clientes.factory.ClientesFactory;
import co.com.sofkau.entrenamiento.curso.clientes.identities.ClienteId;
import co.com.sofkau.entrenamiento.curso.clientes.identities.DestinatarioID;
import co.com.sofkau.entrenamiento.curso.clientes.identities.PersonaId;
import co.com.sofkau.entrenamiento.curso.clientes.identities.RemitenteId;
import co.com.sofkau.entrenamiento.curso.envios.identities.EnviosId;
import co.com.sofkau.entrenamiento.curso.paquete.identities.PaqueteID;

import java.util.List;
import java.util.Map;
/**
 * Agregado ROOT Clientes
 * Se hacen metodos   principales del agregado para comunicacion con  sus entidades
 *
 * @Version 1.0
 * @Author Jhon Stiven Granada Acevedo
 * @Email ticen17.jsga@gmail.com
 * *
 */
public class Clientes extends AggregateEvent<ClienteId> {
    protected EnviosId enviosId;
    protected boolean destinatarioAgregado;
    protected boolean remitenteAgregado;
    protected Map<RemitenteId, Remitente> remitente;
    protected Map<DestinatarioID, Destinatario> destinatario;

    public Clientes(ClienteId entityId) {

        super(entityId);
        appendChange(new ClienteCreado(enviosId, entityId)).apply();
        subscribe(new ClienteEventChange(this));
        ClientesFactory.builder()
                .personas()
                .forEach(persona -> agregarCliente(persona.identity(), persona.getRemitente(), persona.getDestinatario()));

    }


    public static Clientes from(ClienteId entityId, List<DomainEvent> events) {
        var Cliente = new Clientes(entityId);
        events.forEach(Cliente::applyEvent);
        return Cliente;
    }

    public void agregarCliente(PersonaId personaId, Remitente remitente, Destinatario destinatario) {

    }

    public boolean isDestinatarioAgregado() {
        return destinatarioAgregado;
    }

    public boolean isRemitenteAgregado() {
        return remitenteAgregado;
    }
}
