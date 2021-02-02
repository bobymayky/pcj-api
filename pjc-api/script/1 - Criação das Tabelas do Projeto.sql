CREATE TABLE tb_usuario (
    usuario_id   INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
    usuario_login         VARCHAR (250) NOT NULL,
    usuario_nome_completo VARCHAR (250) NOT NULL,
    usuario_senha         VARCHAR (250) NOT NULL
);


CREATE TABLE tb_usuario_sessao (
    usuario_sessao_id   INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
    usuario_id                    INTEGER       NOT NULL,
    usuario_sessao_ip             VARCHAR (50)  NOT NULL,
    usuario_sessao_data_criacao   DATETIME      NOT NULL,
    usuario_sessao_data_expiracao DATETIME      NOT NULL,
    usuario_sessao_ativa          BOOLEAN       NOT NULL DEFAULT true,
    usuario_sessao_token          VARCHAR (250) NOT NULL,
    CONSTRAINT pk_usuario_sessao_usuario_id FOREIGN KEY (usuario_id ) REFERENCES tb_usuario (usuario_id) 
);

CREATE TABLE tb_artista (
   artista_id   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
   artista_nome VARCHAR (250) NOT NULL
);


CREATE TABLE tb_album (
    album_id   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    album_nome VARCHAR (250) NOT NULL,
    artista_id INTEGER       NOT NULL,
    CONSTRAINT pk_album_album_id FOREIGN KEY ( artista_id)  REFERENCES tb_artista (artista_id) 
);



CREATE TABLE tb_album_imagem (
    album_imagem_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    album_id        INTEGER NOT NULL,
    album_imagem_url_foto TEXT    NOT NULL,
    CONSTRAINT pk_album_album_id FOREIGN KEY (album_id) REFERENCES tb_album (album_id) 
);

