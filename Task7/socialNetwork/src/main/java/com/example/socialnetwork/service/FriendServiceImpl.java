package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.ShortClientDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Friend;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final ClientRepository clientRepository;

    @Override
    public Page<ShortClientDTO> getUserFriends(Long clientId, Pageable pageable) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), clientId));

        Page<Friend> friendsPage = friendRepository.findFriendsByFirstClient(client, pageable);
        Page<ShortClientDTO> friendsDTOPage = friendsPage.map(new Function<Friend, ShortClientDTO>() {
            @Override
            public ShortClientDTO apply(Friend friend) {
                return convertClientToDTO(friend.getSecondClient());
            }
        });

        return friendsDTOPage;
    }

    @Override
    @Transactional
    public void deleteFriend(Long firstClientId, Long secondClientId) {
        Client firstClient = clientRepository.findById(firstClientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), firstClientId));
        Client secondClient = clientRepository.findById(firstClientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), secondClientId));

        friendRepository.deleteFriendByFirstClientAndSecondClient(firstClient, secondClient);
        friendRepository.deleteFriendByFirstClientAndSecondClient(secondClient, firstClient);
    }

    @Override
    @Transactional
    public ShortClientDTO addFriend(Long firstClientId, Long secondClientId) {
        Client firstClient = clientRepository.findById(firstClientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), firstClientId));
        Client secondClient = clientRepository.findById(secondClientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), secondClientId));

        friendRepository.save(new Friend(firstClient, secondClient));
        friendRepository.save(new Friend(secondClient, firstClient));
        return convertClientToDTO(secondClient);
    }

    private ShortClientDTO convertClientToDTO(Client client) {
        ShortClientDTO newDTO = new ShortClientDTO(client.getId(), client.getName(), client.getSurname(),
                client.getNickname(), client.getSex());
        if (client.getSchool() != null) {
            newDTO.setSchool(client.getSchool().getId());
        }
        return newDTO;
    }
}
