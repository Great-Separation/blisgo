package blisgo.usecase.port.domain;

import blisgo.domain.member.Member;
import blisgo.usecase.request.member.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberInputPort implements MemberCommand, MemberQuery {
    private final MemberOutputPort port;

    @Override
    public boolean addMember(AddMember command) {
        var member = Member.create(
                command.name(),
                command.email(),
                command.picture()
        );

        return port.create(member);
    }

    @Override
    public boolean updateMember(UpdateMember command) {
        var member = Member.create(
                command.name(),
                command.email(),
                command.picture()
        );

        return port.update(member);
    }

    @Override
    public boolean removeMember(RemoveMember command) {
        var email = command.email();

        return port.delete(email);
    }

    @Override
    public Member getMember(GetMember query) {
        var columns = Map.of("email", query.email());

        return port.read(columns);
    }
}
