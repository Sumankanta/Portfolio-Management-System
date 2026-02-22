package com.suman.portfolio_backend.service.Impl;

import com.suman.portfolio_backend.dto.ClientDTO;
import com.suman.portfolio_backend.entity.Client;
import com.suman.portfolio_backend.repository.ClientRepository;
import com.suman.portfolio_backend.service.interfaces.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        log.info("Creating client: {}", clientDTO.getName());
        Client client = modelMapper.map(clientDTO, Client.class);
        Client saved = clientRepository.save(client);
        log.info("Client created successfully with id: {}", saved.getId());
        return convertToDTO(saved);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        log.info("Update client ID: {}", id);

        Client client = clientRepository.findById(id).orElseThrow(()-> {
            log.error("Client not found with id : {}", id);
            return new RuntimeException("Client not found");
        });

        client.setName(clientDTO.getName());
        client.setLogoUrl(clientDTO.getLogoUrl());
        client.setWebsiteUrl(clientDTO.getWebsiteUrl());
        client.setDescription(client.getDescription());

        log.info("Client updated successfully with ID: {}", id);
        return convertToDTO(client);
    }

    @Override
    public void deleteClient(Long id) {
        log.warn("Deleting client ID: {}", id);
        Client client = clientRepository.findById(id).orElseThrow(()-> {
            log.warn("Attempted to deleteEmployment non-existing client with ID: {}", id);
            return new RuntimeException("Client not found");
        });

        if(client.hasProjects()){
            log.warn("Attempted to deleteEmployment a client having project with clientID: {}", id);
            throw new RuntimeException("Cannot deleteEmployment client with existing projects");
        }

        clientRepository.deleteById(id);
        log.info("Client deleted successfully with ID: {}", id);
    }

    @Override
    public ClientDTO getClientById(Long id) {
        log.debug("Fetching client with ID: {}", id);
        Client client = clientRepository.findById(id).orElseThrow(()-> {
            log.error("Client not found with ID: {}", id);
            return new RuntimeException("Client not found");
        });
        return convertToDTO(client);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        log.debug("Fetching all client");
        List<ClientDTO> clients =  clientRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
        log.info("Total clients fetched: {}", clients.size());
        return clients;
    }

    private ClientDTO convertToDTO(Client client){
        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
        clientDTO.setProjectCount(client.getProjectCount());
        return clientDTO;
    }
}
