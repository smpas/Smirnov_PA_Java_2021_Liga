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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final SchoolRepository schoolRepository;

    @Override
    public ClientProfileDTO getClientById(Long id) {
        return clientRepository.findById(id)
                .map(this::convertClientToProfileDTO)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), id));
    }

    @Override
    public Page<ShortClientDTO> getClientsByParameters(String name, String surname, String nickname, Long schoolId,
                                                       Pageable pageable) {
        List<Client> allClients = (List<Client>) clientRepository.findAll();
        List<ShortClientDTO> clientDTOS = new LinkedList<>();

        for (Client client : allClients) {
            if (name != null && !client.getName().equals(name)) continue;
            if (surname != null && !client.getSurname().equals(surname)) continue;
            if (nickname != null && !client.getNickname().equals(nickname)) continue;
            if (schoolId != null && !client.getSchool().getId().equals(schoolId)) continue;
            clientDTOS.add(convertClientToDTO(client));
        }

        return new PageImpl<>(clientDTOS, pageable, clientDTOS.size());
    }

    @Override
    @Transactional
    public ShortClientDTO addNewClient(ShortClientDTO dto) {
        Client client;

        if (dto.getSchool() != null) {
            School school = schoolRepository.findById(dto.getSchool())
                    .orElseThrow(() -> new EntityNotFoundException(School.class.getName(), dto.getSchool()));

            client = new Client(dto.getName(), dto.getSurname(), dto.getNickname(), dto.getSex(), school);
        } else {
            client = new Client(dto.getName(), dto.getSurname(), dto.getNickname(), dto.getSex());
        }

        return convertClientToDTO(clientRepository.save(client));
    }

    @Override
    @Transactional
    public ShortClientDTO updateClient(ShortClientDTO dto) {
        if (dto.getId() != null) {
            Client client = clientRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), dto.getId()));
            client.setName(dto.getName());
            client.setSurname(dto.getSurname());
            client.setNickname(dto.getNickname());
            client.setSex(dto.getSex());

            if (dto.getSchool() != null) {
                School school = schoolRepository.findById(dto.getSchool())
                        .orElseThrow(() -> new EntityNotFoundException(School.class.getName(), dto.getSchool()));
                client.setSchool(school);
            }
            return convertClientToDTO(clientRepository.save(client));

        } else {
            throw new ClientException("Didn't get client id.");
        }
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        clientRepository.delete(clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), id)));
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
