var editor = new EditorJS({
    holder: 'editorjs',

    readOnly: readOnly,

    tools: {
        checklist: {
            class: Checklist,
            inlineToolbar: true,
        },
        code: {
            class: CodeTool,
            shortcut: 'CMD+SHIFT+D'
        },
        delimiter: Delimiter,
        embed: Embed,
        header: {
            class: Header,
            inlineToolbar: ['link'],
            config: {
                placeholder: 'Header'
            },
            shortcut: 'CMD+SHIFT+H'
        },
        image: {
            class: ImageTool,
            config: {
                field: 'file',
                endpoints: {
                    byFile: '/api/editor/upload/file',
                    byUrl: '/api/editor/upload/url'
                }
            }
        },
        attaches: {
            class: AttachesTool,
            config: {
                field: 'file',
                endpoint: '/api/editor/upload/file',
                errorMessage: "자원이 업로드되지 않습니다"
            }
        },
        inlineCode: {
            class: InlineCode,
            shortcut: 'CMD+SHIFT+M'
        },
        linkTool: {
            class:LinkTool,
            config:{
                endpoint: '/api/editor/link-preview',
                errorMessage: "웹 metadata를 가져오지 못했습니다"
            }
        },
        list: {
            class: List,
            inlineToolbar: true,
        },
        marker: {
            class: Marker,
            shortcut: 'CMD+SHIFT+M'
        },
        quote: {
            class: Quote,
            inlineToolbar: true,
        },
        raw: RawTool,
        table: {
            class: Table,
            inlineToolbar: true,
        },
        warning: Warning,
        // ...
    },

    /**
     * Previously saved data that should be rendered
     */
    onReady: function () {
        var loadedData = JSON.parse(content);
        if (loadedData != null) {
            editor.clear();
            editor.render(loadedData);
        }
    },
    onChange: function (api, event) { },

    rendered: function () { },

    i18n: {
        /**
         * @type {I18nDictionary}
         */
        messages: {
            ui: {
                "blockTunes": {
                    "toggler": {
                        "Click to tune": "옵션 확장",
                        "or drag to move": "또는 드래그하여 이동"
                    },
                },
                "inlineToolbar": {
                    "converter": {
                        "Convert to": "변환하기"
                    }
                },
                "toolbar": {
                    "toolbox": {
                        "Add": "추가"
                    }
                },
                "popover": {
                    "Filter": "필터",
                    "Nothing found": "찾을 수 없습니다",
                }
            },

            toolNames: {
                "Text": "텍스트",
                "Heading": "제목",
                "List": "리스트",
                "Warning": "경고",
                "Checklist": "체크리스트",
                "Quote": "인용문",
                "Code": "코드",
                "Delimiter": "구분선",
                "Raw HTML": "HTML",
                "Table": "테이블",
                "Link": "링크",
                "Marker": "마커",
                "Bold": "굵게",
                "Italic": "기울임",
                "InlineCode": "인라인 코드",
                "Image": "이미지",
                "Attachment": "첨부"
            },

            tools: {
                "warning": {
                    "Title": "제목",
                    "Message": "메시지",
                },

                "link": {
                    "Add a link": "링크 추가",
                },

                "stub": {
                    'The block can not be displayed correctly.': '블록을 올바르게 표시할 수 없습니다.'
                },
                "image": {
                    "Caption": "캡션",
                    "Select an Image": "이미지 선택",
                    "With border": "테두리 추가",
                    "Stretch image": "이미지 확장",
                    "With background": "배경 추가",
                },
                "code": {
                    "Enter a code": "코드를 입력하세요",
                },
                "linkTool": {
                    "Link": "링크",
                    "Couldn't fetch the link data": "링크 데이터를 가져올 수 없습니다",
                    "Couldn't get this link data, try the other one": "이 링크 데이터를 가져올 수 없습니다. 다른 링크를 시도하세요",
                    "Wrong response format from the server": "서버로부터 잘못된 응답 형식",
                },
                "header": {
                    "Header": "헤딩",
                },
                "paragraph": {
                    "Enter something": "내용을 입력하세요",
                },
                "list": {
                    "Ordered": "번호",
                    "Unordered": "글머리",
                },
                "quote": {
                    "Enter a quote": "인용절 입력",
                    "Enter a caption": "캡션 입력",
                },
                "attachment": {
                    "File upload failed": "파일을 올리지 못했습니다",
                    "Select file to upload": "파일을 선택하여 업로드",
                },
                "table": {
                    "With headings": "헤딩 추가",
                    "Without headings": "헤딩 삭제",
                }
            },

            blockTunes: {
                "delete": {
                    "Delete": "삭제",
                    "Click to delete": "클릭하여 삭제",
                },
                "moveUp": {
                    "Move up": "위로 이동"
                },
                "moveDown": {
                    "Move down": "아래로 이동"
                }
            },
        }
    },
});

var saveButton = document.getElementById('save-button');

if (saveButton != null) {
    saveButton.addEventListener('click', function () {
        editor.save()
            .then((savedData) => {
                document.getElementById('editorjs-content').value = JSON.stringify(savedData, null, 0);
                var form = document.getElementById('form');
                form.method = "post";
                form.submit();
            })
            .catch((error) => {
                console.error('Saving error', error);
            });
    });
}