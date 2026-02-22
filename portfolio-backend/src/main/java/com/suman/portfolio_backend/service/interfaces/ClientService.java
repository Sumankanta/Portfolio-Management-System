package com.suman.portfolio_backend.service.interfaces;

import com.suman.portfolio_backend.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);
    ClientDTO updateClient(Long id, ClientDTO clientDTO);
    void deleteClient(Long id);
    ClientDTO getClientById(Long id);
    List<ClientDTO> getAllClients();
}
