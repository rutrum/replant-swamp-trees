{
  description = "Replant Swamp Trees - Fabric mod development environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in
      {
        devShells.default = pkgs.mkShell {
          buildInputs = with pkgs; [
            # Java 21 (required for Minecraft 1.21+)
            jdk21

            # Build tools
            gradle

            # Useful utilities
            just
          ];

          shellHook = ''
            export JAVA_HOME="${pkgs.jdk21}"
            echo "Replant Swamp Trees dev environment"
            echo "Java: $(java -version 2>&1 | head -1)"
            echo "Gradle: $(gradle --version | grep Gradle)"
          '';
        };
      }
    );
}
