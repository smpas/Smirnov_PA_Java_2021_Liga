package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.ClientProfileDTO;
import com.example.socialnetwork.dto.PostDTO;
import com.example.socialnetwork.dto.ShortClientDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Friend;
import com.example.socialnetwork.entity.Post;
import com.example.socialnetwork.entity.School;
import com.example.socialnetwork.exception.ClientException;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, SchoolRepository schoolRepository) {
        this.clientRepository = clientRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<ShortClientDTO> getAllClients() {
        List<Client> clients = (List<Client>) clientRepository.findAll();
        return convertClientListToDTOList(clients);
    }

    @Override
    public ClientProfileDTO getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return convertClientToProfileDTO(client.get());
        } else {
            throw new EntityNotFoundException("Client", id);
        }
    }

    @Override
    public List<ShortClientDTO> getClientsByNameAndSurname(String name, String surname) {
        List<Client> clients = clientRepository.findClientsByNameAndSurname(name, surname);
        return convertClientListToDTOList(clients);
    }

    @Override
    public ClientProfileDTO getClientByNickname(String nickname) {
        Client client = clientRepository.findClientByNickname(nickname);
        if (client != null) {
            return convertClientToProfileDTO(client);
        } else {
            throw new ClientException("Client with nickname " + nickname + " not found.");
        }
    }

    @Override
    public List<ShortClientDTO> getAllClientsBySchoolId(Long schoolId) {
        Optional<School> school = schoolRepository.findById(schoolId);
        if (school.isEmpty()) {
            throw new EntityNotFoundException("School", schoolId);
        }

        List<Client> clients = clientRepository.findClientsBySchool(school.get());
        return convertClientListToDTOList(clients);
    }

    @Override
    public ShortClientDTO addNewClient(ShortClientDTO dto) {
        Client client;

        if (dto.getSchool() != null) {
            Optional<School> school = schoolRepository.findById(dto.getSchool());
            if (school.isPresent()) {
                client = new Client(dto.getName(), dto.getSurname(), dto.getNickname(), dto.getSex(), school.get());
            } else {
                throw new EntityNotFoundException("School", dto.getSchool());
            }
        } else {
            client = new Client(dto.getName(), dto.getSurname(), dto.getNickname(), dto.getSex());
        }

        return convertClientToDTO(clientRepository.save(client));
    }

    @Override
    public ShortClientDTO updateClient(ShortClientDTO dto) {
        if (dto.getId() != null) {
            Optional<Client> changingClient = clientRepository.findById(dto.getId());
            Client client;

            if (changingClient.isPresent()) {
                client = changingClient.get();
                client.setName(dto.getName());
                client.setSurname(dto.getSurname());
                client.setNickname(dto.getNickname());
                client.setSex(dto.getSex());

                if (dto.getSchool() != null) {
                    Optional<School> school = schoolRepository.findById(dto.getSchool());
                    if (school.isPresent()) {
                        client.setSchool(school.get());
                    } else {
                        throw new EntityNotFoundException("School", dto.getSchool());
                    }
                }

            } else {
                throw new EntityNotFoundException("Client", dto.getId());
            }
            return convertClientToDTO(clientRepository.save(client));
        } else {
            throw new ClientException("Didn't get client id.");
        }
    }

    @Override
    public void deleteClient(Long id) {
        Optional<Client> deletingClient = clientRepository.findById(id);

        if (deletingClient.isPresent()) {
            clientRepository.delete(deletingClient.get());
        } else {
            throw new EntityNotFoundException("Client", id);
        }
    }

    private ShortClientDTO convertClientToDTO(Client client) {
        ShortClientDTO newDTO = new ShortClientDTO(client.getId(), client.getName(), client.getSurname(),
                client.getNickname(), client.getSex());
        if (client.getSchool() != null) {
            newDTO.setSchool(client.getSchool().getId());
        }
        return newDTO;
    }

    private List<ShortClientDTO> convertClientListToDTOList(List<Client> clients) {
        List<ShortClientDTO> DTOs = new LinkedList<>();
        for (Client client : clients) {
            DTOs.add(convertClientToDTO(client));
        }
        return DTOs;
    }

    private ClientProfileDTO convertClientToProfileDTO(Client client) {
        List<Post> posts = client.getPosts();
        List<PostDTO> postsDTO = new LinkedList<>();
        for (Post post : posts) {
            PostDTO postDTO = new PostDTO(post.getId(), post.getClient().getId(), post.getDate(), post.getHeader(), post.getText());
            postsDTO.add(postDTO);
        }

        List<Friend> friends = client.getFriends();
        List<Long> friendsDTO = new LinkedList<>();
        for (Friend friend : friends) {
            friendsDTO.add(friend.getSecondClient().getId());
        }

        ClientProfileDTO dto = new ClientProfileDTO(client.getId(), client.getName(), client.getSurname(), client.getNickname(),
                client.getSex(), friendsDTO, postsDTO);
        if (client.getSchool() != null) {
            dto.setSchool(client.getSchool().getId());
        }
        return dto;
    }
}
