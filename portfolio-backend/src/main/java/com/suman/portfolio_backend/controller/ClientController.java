package com.suman.portfolio_backend.controller;

import com.suman.portfolio_backend.dto.ClientDTO;
import com.suman.portfolio_backend.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO clientDTO){

        log.info("REST request to createEmployment client with Id: {}", clientDTO.getId());
        ClientDTO created = clientService.createClient(clientDTO);
        log.info("Client created successfully with ID: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll(){
        log.debug("REST request to fetch all clients");
        List<ClientDTO> clients = clientService.getAllClients();
        log.info("Fetched {} clients", clients.size());
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
        log.debug("REST request to fetch client with ID: {}", id);
        ClientDTO clients = clientService.getClientById(id);
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO clientDTO){
        log.info("REST request to updateEmployment client with ID: {}", id);
        ClientDTO update = clientService.updateClient(id, clientDTO);
        log.info("Client updated successfully with Id: {}", id);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("REST request to deleteEmployment client with ID: {}", id);
        clientService.deleteClient(id);
        log.info("Client deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
