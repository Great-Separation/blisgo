INSERT IGNORE INTO waste (waste_id, name, type, picture, treatment)
VALUES (1001, '{
  "ko": "가격표",
  "en": "Price tag"
}', '{
  "ko": "생활폐기물",
  "en": "Household waste"
}',
        'https://res.cloudinary.com/blisgo/dictionary/1001.webp', NULL),
       (1002, '{
         "ko": "가구류",
         "en": "Furniture"
       }', '{
         "ko": "대형폐기물",
         "en": "Large waste"
       }',
        'https://res.cloudinary.com/blisgo/dictionary/1002.webp', NULL),
       (1003, '{
         "ko": "가발",
         "en": "Wig"
       }', '{
         "ko": "생활폐기물",
         "en": "Household waste"
       }',
        'https://res.cloudinary.com/blisgo/dictionary/1003.webp', NULL),
       (1004, '{
         "ko": "가습기",
         "en": "Humidifier"
       }', '{
         "ko": "폐가전제품",
         "en": "Waste appliances"
       }',
        'https://res.cloudinary.com/blisgo/dictionary/1004.webp', NULL),
       (1005, '{
         "ko": "가위",
         "en": "Scissors"
       }', '{
         "ko": "생활폐기물",
         "en": "Household waste"
       }',
        'https://res.cloudinary.com/blisgo/dictionary/1005.webp',
        '{
          "ko": "<p>다른 재질이 많이 섞인 제품은 분리해서 배출하며, 분리가 어렵다면 <strong>종량제봉투</strong>로 배출</p>",
          "en": "<p>Products mixed with other materials should be separated and discharged, if difficult to separate, discharge in <strong>volume-rate garbage bags</strong></p>"
        }'),
       (1006, '{
         "ko": "개수대",
         "en": "Dog leash"
       }', '{
         "ko": "대형폐기물",
         "en": "Large waste"
       }',
        'https://res.cloudinary.com/blisgo/dictionary/1006.webp', NULL),
       (1007, '{
         "ko": "거울",
         "en": "Mirror"
       }', '{
         "ko": "생활폐기물/대형폐기물",
         "en": "Household waste/Large waste"
       }',
        'https://res.cloudinary.com/blisgo/dictionary/1007.webp',
        '{
          "ko": "<p><strong>크기에 따라</strong> 해당 폐기물로 배출</p>",
          "en": "<p>Discharge as <strong>corresponding waste depending on the size</strong></p>"
        }'),
       (1008, '{
         "ko": "걸레",
         "en": "Rag"
       }', '{
         "ko": "생활폐기물",
         "en": "Household waste"
       }',
        'https://res.cloudinary.com/blisgo/dictionary/1008.webp', NULL),
       (1009, '{
         "ko": "계란껍질",
         "en": "Eggshell"
       }', '{
         "ko": "생활폐기물",
         "en": "Household waste"
       }',
        'https://res.cloudinary.com/blisgo/dictionary/1009.webp', NULL),
       (1010, '{
         "ko": "고무장갑",
         "en": "Rubber gloves"
       }', '{
         "ko": "생활폐기물",
         "en": "Household waste"
       }',
        'https://res.cloudinary.com/blisgo/dictionary/1010.webp', NULL);

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