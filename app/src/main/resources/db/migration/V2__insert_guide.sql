INSERT IGNORE INTO guide (category, docs, content)
VALUES ('IRON',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/rhcjffb.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/rhcjffb.webp"
        }',
        '{
          "ko": "<strong>고철</strong>로 배출",
          "en": "<strong>Scrap metal</strong> for discharge"
        }'),
       ('METAL',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/rmathrzos.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/rmathrzos.webp"
        }',
        '{
          "ko": "<strong>금속캔</strong>으로 배출",
          "en": "<strong>Metal can</strong> for discharge"
        }'),
       ('BULKY',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/eogudvPrlanf.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/eogudvPrlanf.webp"
        }',
        '{
          "ko": "<strong>대형폐기물</strong>로 배출",
          "en": "<strong>Bulky waste</strong> for discharge"
        }'),
       ('STYROFOAM',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/qkfvhgkqtjdtnwl.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/qkfvhgkqtjdtnwl.webp"
        }',
        '{
          "ko": "<strong>스티로폼</strong>으로 배출",
          "en": "<strong>Styrofoam</strong> for discharge"
        }'),
       ('NON_FLAMMABLE',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/qnfdustjd-whdfidwp.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/qnfdustjd-whdfidwp.webp"
        }',
        '{
          "ko": "<strong>불연성폐기물</strong>로 배출",
          "en": "<strong>Non-flammable waste</strong> for discharge"
        }'),
       ('VINYL',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/qlslffb.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/qlslffb.webp"
        }',
        '{
          "ko": "<strong>비닐</strong>로 배출",
          "en": "<strong>Vinyl</strong> for discharge"
        }'),
       ('GLASS',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/dbflqud.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/dbflqud.webp"
        }',
        '{
          "ko": "<strong>유리병류</strong>로 배출",
          "en": "<strong>Glass bottles</strong> for discharge"
        }'),
       ('PROG',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/dmatlranf.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/dmatlranf.webp"
        }',
        '{
          "ko": "<strong>음식물류 폐기물</strong>로 배출",
          "en": "<strong>Food waste</strong> for discharge"
        }'),
       ('CLOTHES',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/dmlfb.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/dmlfb.webp"
        }',
        '{
          "ko": "<strong>의류 및 원단류</strong>로 배출",
          "en": "<strong>Clothes and fabrics</strong> for discharge"
        }'),
       ('ONLY_BOX',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/wjsdydgka.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/wjsdydgka.webp"
        }',
        '{
          "ko": "<strong>전용수거함</strong>으로 배출",
          "en": "<strong>Dedicated collection box</strong> for discharge"
        }'),
       ('PAPER',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/whddl.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/whddl.webp"
        }',
        '{
          "ko": "<strong>종이류</strong>로 배출",
          "en": "<strong>Paper</strong> for discharge"
        }'),
       ('CARTON',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/whddlvor-whddlzjq.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/whddlvor-whddlzjq.webp"
        }',
        '{
          "ko": "<strong>종이팩</strong>으로 배출",
          "en": "<strong>Paper pack</strong> for discharge"
        }'),
       ('HOME_APPLIANCES',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/vPrkwjswpvna.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/vPrkwjswpvna.webp"
        }',
        '{
          "ko": "<strong>폐가전제품</strong>으로 배출",
          "en": "<strong>Waste appliances</strong> for discharge"
        }'),
       ('PLASTIC',
        '{
          "ko": "https://res.cloudinary.com/blisgo/guide/ko/vmffktmxlr.webp",
          "en": "https://res.cloudinary.com/blisgo/guide/en/vmffktmxlr.webp"
        }',
        '{
          "ko": "<strong>플라스틱</strong>으로 배출",
          "en": "<strong>Plastic</strong> for discharge"
        }'),
       ('PAY_AS_YOU_GO_BAG', NULL,
        '{
          "ko": "<strong>종량제봉투</strong>로 배출",
          "en": "<strong>Pay-as-you-go bag</strong> for discharge"
        }'),
       ('SEPARATION_BY_MATERIAL', NULL,
        '{
          "ko": "<strong>재질에 맞게</strong> 배출",
          "en": "<strong>Discharge according to material</strong>"
        }'),
       ('CAUTION', NULL,
        '{
          "ko": "<strong>위험!</strong> 폐기시 주의가 필요함",
          "en": "<strong>Danger!</strong> Caution is needed when disposing"
        }'),
       ('PRO_FACILITY', NULL,
        '{
          "ko": "<strong>전문처리시설</strong>로 배출",
          "en": "<strong>Discharge to a professional facility</strong>"
        }');