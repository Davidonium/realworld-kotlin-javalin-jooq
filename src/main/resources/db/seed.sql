insert into users
(
    id,
    email,
    password,
    username,
    bio,
    image
)
values
(
    1,
    'dhernando@gmail.com',
    '$2a$12$zHejroC0hTulus397kmiheoRi0q4CnkdiXRsw/Ai4C4Z09G0BolFa',
    'dhernando',
    'test bio',
    'http://lorempixel.com/400/200'
),
(
    2,
    'turena@gmail.com',
    '$2a$12$zHejroC0hTulus397kmiheoRi0q4CnkdiXRsw/Ai4C4Z09G0BolFa',
    'turnena',
    'test bio 2',
    'http://lorempixel.com/400/200'
),
(
    3,
    'sjurado@gmail.com',
    '$2a$12$zHejroC0hTulus397kmiheoRi0q4CnkdiXRsw/Ai4C4Z09G0BolFa',
    'sjurado',
    'test bio 3',
    'http://lorempixel.com/400/200'
);

insert into articles
(
    id,
    slug,
    title,
    description,
    body,
    created_at,
    updated_at,
    author_id
)
values
(
    1,
    'lorem-ipsum',
    'Lorem Ipsum',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce interdum aliquam accumsan. In orci urna, pulvinar consequat lacinia ac, molestie vitae ex. Cras bibendum ipsum odio, id auctor magna dictum in. Pellentesque congue eros eget aliquam pharetra. Nulla auctor sem orci, vel vestibulum sem varius eget. Donec odio sem, interdum ut volutpat nec, ornare posuere nulla. Praesent porta id erat vel facilisis. Nunc sed dapibus odio, in pharetra mi. Nullam tellus erat, sodales eget lobortis at, maximus id tortor.',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce interdum aliquam accumsan. In orci urna, pulvinar consequat lacinia ac, molestie vitae ex. Cras bibendum ipsum odio, id auctor magna dictum in. Pellentesque congue eros eget aliquam pharetra. Nulla auctor sem orci, vel vestibulum sem varius eget. Donec odio sem, interdum ut volutpat nec, ornare posuere nulla. Praesent porta id erat vel facilisis. Nunc sed dapibus odio, in pharetra mi. Nullam tellus erat, sodales eget lobortis at, maximus id tortor.

Curabitur sed imperdiet nulla. Sed tincidunt turpis porta, commodo ex sed, commodo urna. Nulla auctor dolor et ligula congue, vel semper ipsum blandit. Nunc tempus leo eget nisl consectetur imperdiet. Curabitur convallis purus bibendum mi imperdiet suscipit. Ut at erat ac lorem aliquam pretium. Phasellus at ligula maximus ipsum vehicula ullamcorper. Nulla dapibus tellus tempus lorem semper, eget convallis enim euismod. Morbi odio metus, pharetra vel tristique non, porta vel magna. Aliquam luctus accumsan tortor, non lacinia lectus scelerisque vel. Donec libero magna, accumsan quis sodales eu, aliquet rutrum nulla. Quisque quis neque nec urna volutpat fringilla id condimentum nisi. Nam sed massa id enim aliquet varius sed ut sem. Morbi accumsan tincidunt dictum.

Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean a felis augue. Quisque molestie varius feugiat. Nullam lacus urna, ornare quis egestas nec, molestie sed nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce consequat fringilla faucibus. Sed vestibulum vel lectus a vulputate. In nec vulputate velit, id rhoncus sem. Suspendisse vulputate ipsum lacus, et tempus lectus mollis at. Ut convallis odio sed dictum aliquet.',
    current_timestamp,
    current_timestamp,
    1
),
(
    2,
    'mauris-accumsan-nulla-ut-dolor-cursus-interdum',
    'Mauris accumsan nulla ut dolor cursus interdum',
    'Mauris accumsan nulla ut dolor cursus interdum. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Phasellus vitae justo ut metus dictum fringilla. Phasellus enim nisi, bibendum egestas purus vel, volutpat molestie nulla. Praesent luctus ipsum justo, id vestibulum nibh porttitor elementum. Proin ullamcorper aliquam metus, a semper felis placerat malesuada. Fusce luctus, sapien id ultrices euismod, massa diam faucibus tellus, id suscipit orci diam eu quam. Nam eget venenatis velit. Nunc eu nisl suscipit, dictum purus in, rhoncus tortor. Integer convallis enim diam, et laoreet ipsum laoreet vel. Aenean nulla velit, hendrerit eu iaculis eu, euismod maximus tortor. Nullam id semper massa. Aliquam ligula erat, ultricies ut lectus ut, gravida rhoncus nisi. Nunc vulputate dolor nulla, iaculis iaculis leo rutrum ut. Aenean justo sem, mollis ac sapien et, mattis volutpat mauris.',
    'Mauris accumsan nulla ut dolor cursus interdum. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Phasellus vitae justo ut metus dictum fringilla. Phasellus enim nisi, bibendum egestas purus vel, volutpat molestie nulla. Praesent luctus ipsum justo, id vestibulum nibh porttitor elementum. Proin ullamcorper aliquam metus, a semper felis placerat malesuada. Fusce luctus, sapien id ultrices euismod, massa diam faucibus tellus, id suscipit orci diam eu quam. Nam eget venenatis velit. Nunc eu nisl suscipit, dictum purus in, rhoncus tortor. Integer convallis enim diam, et laoreet ipsum laoreet vel. Aenean nulla velit, hendrerit eu iaculis eu, euismod maximus tortor. Nullam id semper massa. Aliquam ligula erat, ultricies ut lectus ut, gravida rhoncus nisi. Nunc vulputate dolor nulla, iaculis iaculis leo rutrum ut. Aenean justo sem, mollis ac sapien et, mattis volutpat mauris.

Duis vitae quam non dui molestie sodales. Integer rutrum mauris nisi, ut porta lectus pretium ut. In ultrices quis purus vitae facilisis. Nulla volutpat nulla scelerisque, mollis ipsum sit amet, ultrices dolor. Phasellus lobortis at ex non varius. Sed a suscipit nunc, nec volutpat neque. Nunc placerat nisi vitae dignissim bibendum. Curabitur quis arcu gravida, fermentum mi vitae, accumsan lacus. Duis erat odio, mollis id rhoncus eget, dictum sit amet leo.

In interdum, metus in cursus laoreet, lorem dolor dapibus quam, a sagittis eros tellus ac metus. Etiam laoreet est velit, a ultricies nibh porttitor vel. Proin sit amet nisi placerat, pulvinar elit sit amet, malesuada massa. Integer condimentum, quam ac cursus semper, ligula libero aliquet felis, efficitur hendrerit ante sem quis lectus. Mauris lacinia a quam quis pulvinar. Proin ac orci lorem. Mauris venenatis at neque pulvinar porta.',
    current_timestamp,
    current_timestamp,
    1
),
(
    3,
    'etiam-sodales-nibh-et-nisi-ultricies-rhoncus',
    'Etiam sodales nibh et nisi ultricies rhoncus',
    'Etiam sodales nibh et nisi ultricies rhoncus. Mauris accumsan arcu in sagittis efficitur. Etiam placerat felis in ligula consectetur fermentum a sit amet magna. Phasellus efficitur eros in lectus rhoncus, ac tempus nunc ornare. Donec maximus, ligula a gravida porttitor, massa urna vestibulum velit, sit amet mattis leo mi non urna. Praesent ac velit libero. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.',
    'Etiam sodales nibh et nisi ultricies rhoncus. Mauris accumsan arcu in sagittis efficitur. Etiam placerat felis in ligula consectetur fermentum a sit amet magna. Phasellus efficitur eros in lectus rhoncus, ac tempus nunc ornare. Donec maximus, ligula a gravida porttitor, massa urna vestibulum velit, sit amet mattis leo mi non urna. Praesent ac velit libero. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.

Mauris accumsan nulla ut dolor cursus interdum. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Phasellus vitae justo ut metus dictum fringilla. Phasellus enim nisi, bibendum egestas purus vel, volutpat molestie nulla. Praesent luctus ipsum justo, id vestibulum nibh porttitor elementum. Proin ullamcorper aliquam metus, a semper felis placerat malesuada. Fusce luctus, sapien id ultrices euismod, massa diam faucibus tellus, id suscipit orci diam eu quam. Nam eget venenatis velit. Nunc eu nisl suscipit, dictum purus in, rhoncus tortor. Integer convallis enim diam, et laoreet ipsum laoreet vel. Aenean nulla velit, hendrerit eu iaculis eu, euismod maximus tortor. Nullam id semper massa. Aliquam ligula erat, ultricies ut lectus ut, gravida rhoncus nisi. Nunc vulputate dolor nulla, iaculis iaculis leo rutrum ut. Aenean justo sem, mollis ac sapien et, mattis volutpat mauris.',
    current_timestamp,
    current_timestamp,
    2
);

insert into follows
(
    from_user_id,
    to_user_id
)
values
(
    1,
    2
),
(
    1,
    3
),
(
    2,
    1
);

insert into favorited_articles
(
    article_id,
    user_id
)
values
(
    1,
    1
),
(
    2,
    1
),
(
    2,
    2
);

insert into tags
(
    id,
    name
)
values
(
    1,
    'electronics'
),
(
    2,
    'microservices'
),
(
    3,
    'science'
);

insert into article_to_tag
(
    article_id,
    tag_id
)
values
(
    1,
    2
),
(
    1,
    1
),
(
    2,
    3
);

insert into comments
(
    id,
    body,
    created_at,
    updated_at,
    author_id,
    article_id
)
values
(
    1,
    'this article is just buzzwords',
    current_timestamp,
    null,
    1,
    2
),
(
    2,
    'I love this',
    current_timestamp,
    null,
    2,
    2
);
