package com.banco.controller;

import com.banco.model.Conta;
import com.banco.model.Transacao;
import com.banco.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    // POST /api/contas — criar conta
    @PostMapping
    public ResponseEntity<Conta> criar(@RequestBody Map<String, Object> body) {
        String titular = (String) body.get("titular");
        String cpf = (String) body.get("cpf");
        BigDecimal saldo = body.containsKey("saldoInicial")
            ? new BigDecimal(body.get("saldoInicial").toString())
            : BigDecimal.ZERO;
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarConta(titular, cpf, saldo));
    }

    // GET /api/contas — listar todas
    @GetMapping
    public ResponseEntity<List<Conta>> listar() {
        return ResponseEntity.ok(service.listarContas());
    }

    // GET /api/contas/{id} — buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscar(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarConta(id));
    }

    // DELETE /api/contas/{id} — deletar conta
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable String id) {
        service.deletarConta(id);
        return ResponseEntity.ok(Map.of("mensagem", "Conta deletada com sucesso."));
    }

    // POST /api/contas/{id}/depositar
    @PostMapping("/{id}/depositar")
    public ResponseEntity<Conta> depositar(@PathVariable String id, @RequestBody Map<String, Object> body) {
        BigDecimal valor = new BigDecimal(body.get("valor").toString());
        return ResponseEntity.ok(service.depositar(id, valor));
    }

    // POST /api/contas/{id}/sacar
    @PostMapping("/{id}/sacar")
    public ResponseEntity<Conta> sacar(@PathVariable String id, @RequestBody Map<String, Object> body) {
        BigDecimal valor = new BigDecimal(body.get("valor").toString());
        return ResponseEntity.ok(service.sacar(id, valor));
    }

    // POST /api/contas/{id}/transferir
    @PostMapping("/{id}/transferir")
    public ResponseEntity<Map<String, String>> transferir(
            @PathVariable String id,
            @RequestBody Map<String, Object> body) {
        String destinoId = (String) body.get("destinoId");
        BigDecimal valor = new BigDecimal(body.get("valor").toString());
        service.transferir(id, destinoId, valor);
        return ResponseEntity.ok(Map.of("mensagem", "Transferência realizada com sucesso."));
    }

    // GET /api/contas/{id}/historico
    @GetMapping("/{id}/historico")
    public ResponseEntity<List<Transacao>> historico(@PathVariable String id) {
        return ResponseEntity.ok(service.historico(id));
    }
}