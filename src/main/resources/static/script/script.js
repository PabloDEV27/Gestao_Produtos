const apiBase = 'http://localhost:8080/api';
let listaProd = []; let listaGest = []; let listaEst = [];

document.addEventListener('DOMContentLoaded', carregarTudo);

function mudarAba(idAba, btn) {
    document.querySelectorAll('.aba-conteudo').forEach(a => a.classList.remove('ativa'));
    document.querySelectorAll('.btn-aba').forEach(b => b.classList.remove('ativo'));
    document.getElementById(idAba).classList.add('ativa');
    btn.classList.add('ativo');
}

function aviso(texto, tipo = 'ok') {
    const msg = document.getElementById('msg');
    msg.textContent = texto;
    msg.className = `msg-${tipo}`;
    msg.style.display = 'block';
    setTimeout(() => msg.style.display = 'none', 4000);
}

async function carregarTudo() {
    await buscarGestores();
    await buscarProdutos();
    await buscarEstoques();
    atualizarSelects();
}

// --- PRODUTOS ---
async function buscarProdutos() {
    try {
        const res = await fetch(`${apiBase}/produtos`);
        listaProd = await res.json();
        renderEstoque(); // Renderiza a tabela da view estoque
    } catch (e) { aviso('Erro ao carregar produtos', 'erro'); }
}

document.getElementById('form-produto').addEventListener('submit', async (e) => {
    e.preventDefault();
    const obj = {
        nome: document.getElementById('prod-nome').value,
        tipo: document.getElementById('prod-tipo').value,
        qtdeMinima: document.getElementById('prod-min').value,
        qtdeMaxima: document.getElementById('prod-max').value,
        senhaGestor: document.getElementById('prod-senha').value
    };

    try {
        const res = await fetch(`${apiBase}/produtos`, { method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(obj) });
        const text = await res.text();
        if(text.includes("Erro")) {
            aviso(text, 'erro');
        } else {
            aviso(text, 'ok');
            document.getElementById('form-produto').reset();
            carregarTudo();
        }
    } catch (e) { aviso('Erro de conexão', 'erro'); }
});

// --- ESTOQUES (MISTURA DADOS DE PRODUTO E HISTÓRICO) ---
async function buscarEstoques() {
    try {
        const res = await fetch(`${apiBase}/estoques`);
        listaEst = await res.json();
        renderEstoque();
    } catch (e) { console.error(e); }
}

function renderEstoque() {
    const tb = document.getElementById('tb-estoques');
    tb.innerHTML = '';
    listaProd.forEach(p => {
        // Encontrar a última movimentação deste produto no estoque para o Hover (tooltip)
        const movimentos = listaEst.filter(e => e.produto && e.produto.id === p.id);
        let txtHover = "Nenhuma atualização registrada";
        let strData = "-";

        if (movimentos.length > 0) {
            const ultimoMovimento = movimentos[movimentos.length - 1]; // Assume que o último adicionado é o mais recente
            const gestNome = ultimoMovimento.gestor ? ultimoMovimento.gestor.nome : '?';

            // Formatação da data (Tratamento para LocalDateTime do Java)
            if(ultimoMovimento.dataMovimento) {
                const d = new Date(ultimoMovimento.dataMovimento);
                strData = d.toLocaleDateString('pt-BR') + ' ' + d.toLocaleTimeString('pt-BR', {hour: '2-digit', minute:'2-digit'});
            }
            txtHover = `Alterado por ${gestNome} em ${strData}`;
        }

        tb.innerHTML += `
                    <tr>
                        <td>${p.nome}</td>
                        <td>${p.qtdeMinima}</td>
                        <td>${p.qtdeMaxima}</td>
                        <td><strong>${p.qtde}</strong></td>
                        <td><span class="tooltip" title="${txtHover}">${strData}</span></td>
                    </tr>`;
    });
}

document.getElementById('form-estoque').addEventListener('submit', async (e) => {
    e.preventDefault();
    const idProd = document.getElementById('est-produto').value;
    const idGestor = document.getElementById('est-gestor').value;
    const novaQtde = document.getElementById('est-qtde').value;

    const obj = { produto: { id: idProd }, gestor: { id: idGestor } };

    try {
        const res = await fetch(`${apiBase}/estoques?novaQtde=${novaQtde}`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(obj)
        });
        const responseTxt = await res.text();

        if(responseTxt.includes("ALERTA")) {
            aviso(responseTxt, 'alerta');
        } else {
            aviso(responseTxt, 'ok');
        }

        document.getElementById('est-qtde').value = '';
        carregarTudo();
    } catch (e) { aviso('Erro', 'erro'); }
});

// --- GESTORES ---
async function buscarGestores() {
    try {
        const res = await fetch(`${apiBase}/gestores`);
        listaGest = await res.json();
        renderGestores();
    } catch (e) { console.error(e); }
}

function renderGestores() {
    const tb = document.getElementById('tb-gestores');
    tb.innerHTML = '';
    listaGest.forEach(g => {
        tb.innerHTML += `
                    <tr>
                        <td>${g.nome}</td><td>${g.email || ''}</td>
                        <td class="acoes"><button class="editar">Editar</button></td>
                    </tr>`;
    });
}

document.getElementById('form-gestor').addEventListener('submit', async (e) => {
    e.preventDefault();
    const obj = {
        nome: document.getElementById('gest-nome').value,
        email: document.getElementById('gest-email').value,
        chaveAcesso: document.getElementById('gest-senha').value
    };
    try {
        await fetch(`${apiBase}/gestores`, { method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(obj) });
        aviso('Gestor adicionado!');
        document.getElementById('form-gestor').reset();
        carregarTudo();
    } catch (e) { aviso('Erro', 'erro'); }
});

function atualizarSelects() {
    const selProd = document.getElementById('est-produto');
    const selGest = document.getElementById('est-gestor');
    selProd.innerHTML = '<option value="">Selecione...</option>';
    listaProd.forEach(p => selProd.innerHTML += `<option value="${p.id}">${p.nome}</option>`);
    selGest.innerHTML = '<option value="">Selecione...</option>';
    listaGest.forEach(g => selGest.innerHTML += `<option value="${g.id}">${g.nome}</option>`);
}