function botaoDeletarDaTela() {
    var id = $("#id").val();

    if (id != null && id.trim() != "") {
      deleteUser(id);
      document.getElementById("formCadastroUser").reset();
    } else {
      alert(
        "Os campos estão vazios, selecione um usuario valido para deleltar."
      );
    }
  }

  function deleteUser(id) {
    if (confirm("Deseja realmente deletar?")) {
      $.ajax({
        method: "DELETE",
        url: "delete",
        data: "idUser=" + id,
        success: function (response) {
          $("#" + id).remove();
          alert(response);
        },
      }).fail(function (xhr, status, errorThrown) {
        alert("Erro ao deletar usuário por ID: " + xhr.responseText);
      });
    }
  }
  function pesquisarUser() {
    var nome = $("#nomeBusca").val();

    if (nome != null && nome.trim() != "") {
      $.ajax({
        method: "GET",
        url: "buscarPorNome",
        data: "name=" + nome,
        success: function (response) {
          $("#tabelaResultados > tbody > tr").remove();

          for (var i = 0; i < response.length; i++) {
            $("#tabelaResultados > tbody").append(
              '<tr id="' +
                response[i].id +
                '"><td>' +
                response[i].id +
                "</td><td>" +
                response[i].nome +
                '</td><td><button type="button" class="btn btn-warning" onclick="colocarEmEdicao(' +
                response[i].id +
                ')">Visualizar</button></td><td><button type="button" onclick="deleteUser(' +
                response[i].id +
                ')" class="btn btn-danger">Deletar</button></td></tr>'
            );
          }
        },
      }).fail(function (xhr, status, errorThrown) {
        alert("Erro ao buscar usuário: " + xhr.responseText);
      });
    }
  }

  function colocarEmEdicao(id) {
    $.ajax({
      method: "GET",
      url: "buscaruserid",
      data: "idUser=" + id,
      success: function (response) {
        $("#id").val(response.id);
        $("#nome").val(response.nome);
        $("#idade").val(response.idade);

        $("#modalPesquisaUser").modal("hide");
      },
    }).fail(function (xhr, status, errorThrown) {
      alert("Erro ao buscar usuário por ID: " + xhr.responseText);
    });
  }

  function salvarUsuario() {
    var id = $("#id").val();
    var nome = $("#nome").val();
    var idade = $("#idade").val();


    if(nome == null || nome != null && nome.trim() == ''){
        $("#nome").focus();
        alert("Informe o nome");
        return;
    }
    if(idade == null || idade != null && idade.trim() == ''){
        $("#idade").focus();
        alert("Informe a idade");
        return;
    }

   
    $.ajax({
      method: "POST",
      url: "salvar",
      data: JSON.stringify({ id: id, nome: nome, idade: idade }),
      contentType: "application/json; charset-utf-8",
      success: function (response) {
        $("#id").val(response.id);
        alert("Salvo com sucesso!");
      },
    }).fail(function (xhr, status, errorThrown) {
      alert("Erro ao salvar: " + xhr.responseText);
    });
  }