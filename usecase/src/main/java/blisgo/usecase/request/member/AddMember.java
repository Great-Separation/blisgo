package blisgo.usecase.request.member;

public record AddMember(
        String email,
        String name,
        String picture
)  {
}
